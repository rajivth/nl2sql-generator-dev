package com.nl2sql.sql.intent.prompt;


import com.nl2sql.common.PromptBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IntentPrompt implements PromptBuilder<String,Prompt> {

    @Value("classpath:prompt-templates/system-intent.st")
    private Resource systemTemplate;

    @Value("classpath:prompt-templates/user-intent.st")
    private Resource userTemplate;

    @Override
    public Prompt build(String question) {
        Message systemMessage = new PromptTemplate(systemTemplate).createMessage();
        Message userMessage = new PromptTemplate(userTemplate).createMessage(Map.of("question", question));
        return new Prompt(List.of(systemMessage, userMessage));

    }

}
