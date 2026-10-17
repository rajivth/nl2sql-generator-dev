package com.nl2sql.vector.embedding;

import com.nl2sql.common.JacksonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;
    private final EmbeddingTextBuilder embeddingTextBuilder;
    private final JacksonConverter jacksonConverter;

    public <T extends EmbeddableVector> T create(Object sourceType, Class<T> targetType, String objectType) {
        if (sourceType == null) {
            return null;
        }
        T entity = jacksonConverter.convertSourceToTarget(sourceType, targetType);
        entity.setObjectType(objectType);

        List<String> businessKeywords = entity.getBusinessKeywords();
        var businessKeywordsMap = Map.of("businessKeywords", businessKeywords);
        String text = embeddingTextBuilder.build(businessKeywordsMap);
        float[] embedding = embeddingModel.embed(text);
        entity.setEmbedding(embedding);
        return entity;
    }

    public float[] fetch(String text) {
        return embeddingModel.embed(text);
    }
}
