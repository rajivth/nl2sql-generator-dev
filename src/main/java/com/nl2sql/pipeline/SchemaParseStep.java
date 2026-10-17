package com.nl2sql.pipeline;

import com.nl2sql.schema.parser.SchemaParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class SchemaParseStep implements Pipeline {
    final SchemaParser schemaParser;
    @Override
    public void process(PipelineContext context) {
        log.debug("Processing Schema parse Step-2");
        var databaseSchemaList = context.getDdlFiles().stream().map(schemaParser::parse).toList();
        context.setSchemaFiles(databaseSchemaList);
    }
}
