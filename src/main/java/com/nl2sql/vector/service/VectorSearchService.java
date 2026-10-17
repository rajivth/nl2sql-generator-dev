package com.nl2sql.vector.service;

import com.nl2sql.vector.model.VectorSearchResponse;

public interface VectorSearchService {
    VectorSearchResponse search(String question, int topK);
}
