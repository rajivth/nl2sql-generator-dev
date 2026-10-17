package com.nl2sql.sql.intent.analyzer;
import com.nl2sql.sql.intent.model.QueryIntent;
import org.springframework.ai.chat.prompt.Prompt;

public interface IntentAnalyzer {
    QueryIntent analyze(String question);
    String  generate(Prompt prompt);
}
