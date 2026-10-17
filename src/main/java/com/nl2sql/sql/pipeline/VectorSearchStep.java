package com.nl2sql.sql.pipeline;

import com.nl2sql.vector.service.PgVectorSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class VectorSearchStep implements SqlQueryPipelineStep {
    static final String NO_SCHEMA_MATCH = "-- NO_SCHEMA_MATCH";

    final PgVectorSearchService pgVectorSearchService;

    @Override
    public void process(SqlQueryPipelineContext context) {
        log.debug("Processing VectorSearchStep Step-1");
        var vectorSearchResponse = pgVectorSearchService.search(context.getQuestion(), 2);
        context.setVectorSearch(vectorSearchResponse);
        if (vectorSearchResponse.tables() == null || vectorSearchResponse.tables().isEmpty()) {
            log.debug("No matching tables for question; skipping SQL LLM");
            context.setSqlResult(NO_SCHEMA_MATCH);
        }
    }
}
