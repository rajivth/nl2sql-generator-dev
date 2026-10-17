package com.nl2sql.pipeline;

import com.nl2sql.schema.loader.SchemaLoaderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class SchemaLoadStep implements Pipeline {

    final SchemaLoaderFactory schemaLoaderFactory;


    @Override
    public void process(PipelineContext context) {
        log.debug("Processing Schema Load Step-1");
        var ddlStrFiles = schemaLoaderFactory.loadSchemas(context.getSchemaSourceType());
        context.setDdlFiles(ddlStrFiles);
    }
}
