package com.nl2sql.vector.embedding;

import com.fasterxml.jackson.databind.JsonNode;
import com.nl2sql.common.JacksonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmbeddingTextBuilder {

    private final JacksonConverter jacksonConverter;

    public String build(Object object) {
        JsonNode node = jacksonConverter.convertObjectToJsonTreeNode(object);
        StringBuilder text = new StringBuilder();
        node.fields().forEachRemaining(entry -> {
            JsonNode value = entry.getValue();
            if (value.isArray()) {
                text.append(entry.getKey()).append(": ").append(value.toString()).append("\n");
            } else {
                text.append(entry.getKey()).append(": ").append(value.asText()).append("\n");
            }
        });

        return text.toString();
    }
}
