package com.nl2sql.sql.intent.llm;

import com.nl2sql.common.JacksonConverter;
import com.nl2sql.sql.intent.analyzer.IntentAnalyzer;
import com.nl2sql.sql.intent.model.QueryIntent;
import com.nl2sql.common.PromptBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class LlmIntentAnalyzer implements IntentAnalyzer {

    private final ChatClient chatClient;
    private final PromptBuilder<String, Prompt> promptBuilder;

    private final JacksonConverter jacksonConverter;

    @Override
    public QueryIntent analyze(String question) {
        log.debug("analyze question \n {} \n start with time {}", question, Instant.now());
        Prompt prompt = promptBuilder.build(question);
        String response = chatClient.prompt(prompt)
                .options(OllamaChatOptions.builder().format("json"))
                .call()
                .content();
        log.debug("analyze question   response \n {} \n with time {}", response, Instant.now());
        return jacksonConverter.convertJsonToObject(response, QueryIntent.class);

    }

    @Override
    public String generate(Prompt prompt) {
        log.debug("Build Generate Query start with time {}", Instant.now());
        String response = chatClient.prompt(prompt).call().content();
        log.debug("Generate Query is \n  {} ", response);
        log.debug("end with time {}", Instant.now());
        return response;
    }

}
