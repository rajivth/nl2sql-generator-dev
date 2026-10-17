package com.nl2sql.sql.pipeline;

import com.nl2sql.common.PromptBuilder;
import com.nl2sql.sql.intent.model.SqlGenerationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class SqlBuilderPrepareStep implements SqlQueryPipelineStep {
    final PromptBuilder<SqlGenerationRequest, Prompt> promptBuilder;

    @Override
    public void process(SqlQueryPipelineContext context) {
        log.debug("Processing SqlBuilderPrepareStep -2");
        if (context.getSqlResult() != null) {
            return;
        }
        var vectorSearch = context.getVectorSearch();
        var sqlGenerationInputRequest = SqlGenerationRequest.builder()
                .question(context.getQuestion())
                .tables(orEmpty(vectorSearch == null ? null : vectorSearch.tables()))
                .columns(orEmpty(vectorSearch == null ? null : vectorSearch.columns()))
                .relations(orEmpty(vectorSearch == null ? null : vectorSearch.relations()))
                .build();
        context.setPrompt(promptBuilder.build(sqlGenerationInputRequest));
    }

    private static <T> List<T> orEmpty(List<T> values) {
        return values == null ? List.of() : values;
    }
}
