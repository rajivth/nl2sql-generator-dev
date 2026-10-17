package com.nl2sql.sql.pipeline;

public interface SqlQueryPipelineStep {

    void process(SqlQueryPipelineContext context);
}
