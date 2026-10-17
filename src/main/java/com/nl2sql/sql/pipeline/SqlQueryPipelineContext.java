package com.nl2sql.sql.pipeline;

import com.nl2sql.vector.model.VectorSearchResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.prompt.Prompt;

@Builder
@Getter
@Setter
public class SqlQueryPipelineContext {

    private String question;
    private String sqlResult;
    private VectorSearchResponse vectorSearch;
    private Prompt prompt;
}
