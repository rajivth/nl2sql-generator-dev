package com.nl2sql.schema.metadata.llm;

import com.nl2sql.schema.metadata.llm.response.BusinessMetaResponse;
import com.nl2sql.schema.metadata.prompt.BusinessMetadataPayloadPrompt;

public interface LlmBusinessMetaAnalyzer {
     BusinessMetaResponse businessMetaGeneration(BusinessMetadataPayloadPrompt prompt);
}
