package com.nl2sql.schema.metadata.prompt;

import com.nl2sql.common.JacksonConverter;
import com.nl2sql.common.PromptBuilder;
import com.nl2sql.schema.model.ColumnProperty;
import com.nl2sql.schema.model.DatabaseSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BusinessMetadataPromptBuilder implements PromptBuilder<DatabaseSchema, BusinessMetadataPayloadPrompt> {

    @Value("classpath:prompt-templates/table-metadata.st")
    private Resource tableTemplate;

    @Value("classpath:prompt-templates/column-metadata.st")
    private Resource columnTemplate;

    @Value("classpath:prompt-templates/relationship-metadata.st")
    private Resource relationshipTemplate;

    @Autowired
    private JacksonConverter jacksonConverter;


    public Prompt buildTablePrompt(DatabaseSchema schema) {
        Map<String, Object> variables = Map.of("schemaName", schema.schemaName(), "tableName", schema.tableInfo().tableName());
        var promptTemplate = PromptTemplate.builder().resource(tableTemplate).variables(variables).build();
        return promptTemplate.create();

    }

    public Prompt buildColumnPrompt(DatabaseSchema schema) {
        List<ColumnProperty> columns = schema.tableInfo().columns().stream().map(
                column -> ColumnProperty.builder()
                        .columnName(column.columnName())
                        .dataType(column.dataType())
                        .nullable(column.nullable())
                        .primaryKey(column.primaryKey()).build()).toList();

        ColumnPayloadRequestPrompt input = ColumnPayloadRequestPrompt.builder().
                schemaName(schema.schemaName()).
                tableName(schema.tableInfo().tableName()).
                columns(columns).build();

        String columnsJson = jacksonConverter.convertObjectToStr(input);
        Map<String, Object> variables = Map.of("schemaName", schema.schemaName(), "tableName", schema.tableInfo().tableName(), "columns", columnsJson);
        return PromptTemplate.builder().resource(columnTemplate).variables(variables).build().create();
    }

    public Prompt buildRelationshipPrompt(DatabaseSchema schema) {
        var relationships = schema.tableInfo().relation();

        var input = RelationshipPayloadRequestPrompt.builder()
                .schemaName(schema.schemaName())
                .tableName(schema.tableInfo().tableName())
                .relationInfos(relationships)
                .build();

        String relationshipJsonStr = jacksonConverter.convertObjectToStr(input);
        log.debug("Relationship JSON: {}", relationshipJsonStr);
        Map<String, Object> variables = Map.of("relationshipJson", relationshipJsonStr);

        return PromptTemplate.builder()
                .resource(relationshipTemplate)
                .variables(variables)
                .build()
                .create();
    }

    @Override
    public BusinessMetadataPayloadPrompt build(DatabaseSchema schema) {
        Prompt tablePrompt = buildTablePrompt(schema);
        Prompt columnPrompt = buildColumnPrompt(schema);
        Prompt relationshipPrompt = buildRelationshipPrompt(schema);
        return BusinessMetadataPayloadPrompt.builder().tablePrompt(tablePrompt).
                columnPrompt(columnPrompt).
                relationshipPrompt(relationshipPrompt).build();
    }
}
