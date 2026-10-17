package com.nl2sql.sql.intent.prompt;


import com.nl2sql.common.JacksonConverter;
import com.nl2sql.common.PromptBuilder;
import com.nl2sql.sql.intent.model.SqlGenerationRequest;
import com.nl2sql.vector.model.ColumnPromptRequestDTO;
import com.nl2sql.vector.model.RelationshipPromptRequestDTO;
import com.nl2sql.vector.model.TablePromptRequestDTO;
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
public class SqlGenerateQueryPrompt implements PromptBuilder<SqlGenerationRequest, Prompt> {

    private final JacksonConverter jacksonConverter;
    @Value("classpath:prompt-templates/system-sql-generate.st")
    private Resource systemTemplate;
    @Value("classpath:prompt-templates/user-sql-generate.st")
    private Resource userTemplate;

    @Override
    public Prompt build(SqlGenerationRequest sqlGenerationRequest) {
        Message systemMessage = new PromptTemplate(systemTemplate).createMessage();

        var tables = sqlGenerationRequest.tables().stream().map(tableEntity ->
                jacksonConverter.convertSourceToTarget(tableEntity, TablePromptRequestDTO.class)).toList();

        var columns = sqlGenerationRequest.columns().stream().map(columnEntity ->
                jacksonConverter.convertSourceToTarget(columnEntity, ColumnPromptRequestDTO.class)).toList();

        var relations = sqlGenerationRequest.relations().stream().map(relationEntity ->
                jacksonConverter.convertSourceToTarget(relationEntity, RelationshipPromptRequestDTO.class)).toList();

        Message userMessage = new PromptTemplate(userTemplate).createMessage(Map.of(
                "question", sqlGenerationRequest.question(),
                "tables", jacksonConverter.convertObjectToStr(tables),
                "columns", jacksonConverter.convertObjectToStr(columns),
                "relations", jacksonConverter.convertObjectToStr(relations)));
        return new Prompt(List.of(systemMessage, userMessage));

    }

}
