package com.nl2sql.vector.embedding;

import java.util.List;

public interface EmbeddableVector {
    void setEmbedding(float[] embedding);
    void setObjectType(String objectType);
    List<String> getBusinessKeywords();
}
