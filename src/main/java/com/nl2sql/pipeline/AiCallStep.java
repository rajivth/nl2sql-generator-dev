package com.nl2sql.pipeline;

import com.nl2sql.schema.metadata.llm.LlmBusinessMetaAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(4)
public class AiCallStep implements Pipeline {
    final LlmBusinessMetaAnalyzer businessMetaAnalyzer;

    @Override
    public void process(PipelineContext context) {
        log.debug("Processing LlmBusinessMetaAnalyzer step-4...");
        var businessMetaResponses = context.getBusinessLoadPrompts().stream().map(businessMetaAnalyzer::businessMetaGeneration).toList();
        context.setBusinessMetaResponses(businessMetaResponses);
    }
}
