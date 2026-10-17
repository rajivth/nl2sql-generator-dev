package com.nl2sql.pipeline;

import com.nl2sql.common.ApplicationConstant;
import com.nl2sql.common.JacksonConverter;
import com.nl2sql.schema.metadata.llm.response.ColumnMetadataResponse;
import com.nl2sql.schema.metadata.llm.response.RelationshipMetadataResponse;
import com.nl2sql.vector.embedding.EmbeddingService;
import com.nl2sql.vector.model.BusinessMetaDataVectorDoc;
import com.nl2sql.vector.model.ColumnMetadataVectorEntity;
import com.nl2sql.vector.model.RelationshipMetadataVectorEntity;
import com.nl2sql.vector.model.TableMetadataVectorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(6)
public class MetadataDocumentStep implements Pipeline {
    final JacksonConverter jacksonConverter;
    final EmbeddingService embeddingService;

    @Override
    public void process(PipelineContext context) {
        var vectorDocs = context.getBusinessMetaResponses().stream().map(businessMetaResponse -> {
            var tableVector = jacksonConverter.convertSourceToTarget(businessMetaResponse.businessMetadata(), TableMetadataVectorEntity.class);
            var tableMetadataVectorDoc = embeddingService.create(businessMetaResponse.businessMetadata(), TableMetadataVectorEntity.class, ApplicationConstant.TABLE_LABEL);

            var columnMetadata = businessMetaResponse.columnMetadata();
            var schemaName = columnMetadata.schemaName();
            var tableName = columnMetadata.tableName();
            var columnMetadataVectorDocs = prepareColumVectorDoc(schemaName, tableName, columnMetadata);
            var relationshipMetadataResponse = businessMetaResponse.relationshipMetadata();
            var relationshipMetadataVectorDocs = prepareRelationVectorDoc(schemaName, tableName, relationshipMetadataResponse);

            return BusinessMetaDataVectorDoc.builder().table(tableMetadataVectorDoc).
                    columns(columnMetadataVectorDocs).relationships(relationshipMetadataVectorDocs).
                    build();
        }).toList();

        context.setVectorDocs(vectorDocs);
    }

    private List<RelationshipMetadataVectorEntity> prepareRelationVectorDoc(String schemaName, String tableName, RelationshipMetadataResponse relationshipMetadataResponse) {
        return relationshipMetadataResponse.relationInfos().stream().map(relationProperty -> {
            var relationMetadataVectorDoc = embeddingService.create(relationProperty, RelationshipMetadataVectorEntity.class, ApplicationConstant.RELATION_LABEL);
            relationMetadataVectorDoc.setSchemaName(schemaName);
            relationMetadataVectorDoc.setTableName(tableName);
            return relationMetadataVectorDoc;
        }).toList();
    }

    private List<ColumnMetadataVectorEntity> prepareColumVectorDoc(String schemaName, String tableName, ColumnMetadataResponse columnMetadata) {
        return columnMetadata.columns().stream().map(column -> {
            var columnMetadataVectorDoc = embeddingService.create(column, ColumnMetadataVectorEntity.class, ApplicationConstant.COLUMN_LABEL);
            columnMetadataVectorDoc.setSchemaName(schemaName);
            columnMetadataVectorDoc.setTableName(tableName);
            return columnMetadataVectorDoc;
        }).toList();

    }


}
