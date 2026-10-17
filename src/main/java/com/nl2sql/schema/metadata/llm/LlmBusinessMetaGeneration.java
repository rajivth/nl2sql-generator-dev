package com.nl2sql.schema.metadata.llm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nl2sql.common.JacksonConverter;
import com.nl2sql.schema.metadata.llm.response.BusinessMetaResponse;
import com.nl2sql.schema.metadata.llm.response.ColumnMetadataResponse;
import com.nl2sql.schema.metadata.llm.response.RelationshipMetadataResponse;
import com.nl2sql.schema.metadata.dto.BusinessMetadata;
import com.nl2sql.schema.metadata.prompt.BusinessMetadataPayloadPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class LlmBusinessMetaGeneration implements LlmBusinessMetaAnalyzer {

    private final ChatClient chatClient;
    private final JacksonConverter jacksonConverter;


    @Override
    public BusinessMetaResponse businessMetaGeneration(BusinessMetadataPayloadPrompt prompt) {
        log.debug("business Meta generation start with time {}", Instant.now());

        CompletableFuture<BusinessMetadata> tableFuture = CompletableFuture.supplyAsync(() ->
                generateTableMetadata(prompt.tablePrompt()));
        CompletableFuture<ColumnMetadataResponse> columnFuture = CompletableFuture.supplyAsync(() ->
                generateColumnMetadata(prompt.columnPrompt()));

        CompletableFuture<RelationshipMetadataResponse> relationFuture = CompletableFuture.supplyAsync(() ->
                generateRelationshipMetadata(prompt.relationshipPrompt()));
        CompletableFuture.allOf(tableFuture, columnFuture, relationFuture).join();


        log.debug("business meta data time end with {}", Instant.now());
        return BusinessMetaResponse.builder().businessMetadata(tableFuture.join()).
                columnMetadata(columnFuture.join()).
                relationshipMetadata(relationFuture.join()).build();
    }

    private BusinessMetadata generateTableMetadata(Prompt prompt) {
        String response = callJson(prompt);
        log.info("table response {}", response);
        return jacksonConverter.convertJsonToObject(response, BusinessMetadata.class);
    }

    private ColumnMetadataResponse generateColumnMetadata(Prompt prompt) {
        String response = callJson(prompt);
        log.info("column response {}", response);
        return jacksonConverter.convertJsonToObject(response, new TypeReference<ColumnMetadataResponse>() {
        });
    }

    private RelationshipMetadataResponse generateRelationshipMetadata(Prompt prompt) {
        String response = callJson(prompt);
        log.info("relationship response {}", response);
        return jacksonConverter.convertJsonToObject(response, new TypeReference<RelationshipMetadataResponse>() {
        });
    }

    private String callJson(Prompt prompt) {
        return chatClient.prompt(prompt)
                .options(OllamaChatOptions.builder().format("json"))
                .call()
                .content();
    }
}
