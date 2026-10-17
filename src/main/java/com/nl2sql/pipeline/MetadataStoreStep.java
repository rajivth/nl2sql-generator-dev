package com.nl2sql.pipeline;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nl2sql.common.JacksonConverter;
import com.nl2sql.schema.loader.JsonFile;
import com.nl2sql.schema.loader.JsonLoader;
import com.nl2sql.schema.metadata.dto.BusinessMetadata;
import com.nl2sql.schema.metadata.dto.BusinessMetadataColumn;
import com.nl2sql.schema.metadata.llm.response.BusinessMetaResponse;
import com.nl2sql.schema.metadata.llm.response.ColumnMetadataResponse;
import com.nl2sql.schema.metadata.llm.response.RelationshipMetadataResponse;
import com.nl2sql.schema.model.RelationProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(5)
public class MetadataStoreStep implements Pipeline {

    private final JsonLoader jsonLoader;
    private final JacksonConverter jacksonConverter;

    @Override
    public void process(PipelineContext context) {
        log.debug("Processing MetadataStoreStep -5...");
        var files = jsonLoader.load();
        Map<String, List<JsonFile>> groupedFiles = files.stream().collect(Collectors.groupingBy(file -> extractTableName(file.fileName())));

        var businessMetaResponses = groupedFiles.entrySet().stream().map(entry ->
                createBusinessMetaResponse(entry.getKey(), entry.getValue())).toList();
        context.setBusinessMetaResponses(businessMetaResponses);

    }

    private String extractTableName(String fileName) {
        return fileName.replaceAll("(?i)_(table|column|relation)\\.json$", "");
    }

    private BusinessMetaResponse createBusinessMetaResponse(String tableName, List<JsonFile> files) {
        log.info("Loading metadata for table: {}", tableName);
        BusinessMetadata businessMetadata = files.stream().filter(file -> isType(file.fileName(),
                        "_table.json")).findFirst().
                map(file -> jacksonConverter.convertJsonToObject(file.content(),
                        BusinessMetadata.class)).orElse(null);

        List<BusinessMetadataColumn> businessMetadataColumns = files.stream()
                .filter(file -> isType(file.fileName(), "_column.json"))
                .findFirst()
                .map(file -> jacksonConverter.convertJsonToObject(
                        file.content(),
                        new TypeReference<List<BusinessMetadataColumn>>() {
                        }
                ))
                .orElse(List.of());
        var columnMetadata = ColumnMetadataResponse.builder().
                schemaName("HGAPPSAM").tableName(tableName).
                columns(businessMetadataColumns).
                build();


        List<RelationProperty> relationProperties = files.stream()
                .filter(file -> isType(file.fileName(), "_relation.json"))
                .findFirst()
                .map(file -> jacksonConverter.convertJsonToObject(
                        file.content(),
                        new TypeReference<List<RelationProperty>>() {
                        }
                ))
                .orElse(List.of());

        var relationshipMetadata = RelationshipMetadataResponse.builder().
                schemaName("HGAPPSAM").tableName(tableName).
                relationInfos(relationProperties).build();
        return new BusinessMetaResponse(businessMetadata, columnMetadata, relationshipMetadata);
    }

    private boolean isType(String fileName, String suffix) {
        return fileName.toLowerCase().endsWith(suffix.toLowerCase());
    }
}
