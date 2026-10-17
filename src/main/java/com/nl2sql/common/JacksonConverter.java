package com.nl2sql.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JacksonConverter {

    private final ObjectMapper objectMapper;

    public JsonNode convertObjectToJsonTreeNode(Object object) {
        log.debug("Converting object to Json Tree node: {}", object);
        return objectMapper.valueToTree(object);
    }

    public String convertObjectToStr(Object object) {
        try {
            log.debug("Converting object to string: {}", object);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    public <T> T convertSourceToTarget(Object source, Class<T> targetType) {
        log.debug("Converting source to target: {} with target {}", source,targetType);
        return objectMapper.convertValue(source, targetType);
    }

    public <T> T convertJsonToObject(String jsonInput, Class<T> targetType) {
        try {
            log.debug("Converting json to object: {} \n with target Type {}", jsonInput,targetType);
            return objectMapper.readValue(jsonInput, targetType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    public <T> T convertJsonToObject(String json, TypeReference<T> typeReference) {
        try {
            log.debug("Convert json to object: {} \n with typeRef {}", json,typeReference);
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }


}
