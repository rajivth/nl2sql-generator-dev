package com.nl2sql.pipeline;

import com.nl2sql.common.PromptBuilder;
import com.nl2sql.schema.metadata.prompt.BusinessMetadataPayloadPrompt;
import com.nl2sql.schema.model.DatabaseSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class PromptBuilderStep implements Pipeline {
    final PromptBuilder<DatabaseSchema, BusinessMetadataPayloadPrompt>  promptBuilder;
    @Override
    public void process(PipelineContext context) {
        log.debug("Prompt builder started step- 3");
        var businessMetadataPayLoadPrompts = context.getSchemaFiles().stream().map(promptBuilder::build).toList();
    context.setBusinessLoadPrompts(businessMetadataPayLoadPrompts);
    }
}
