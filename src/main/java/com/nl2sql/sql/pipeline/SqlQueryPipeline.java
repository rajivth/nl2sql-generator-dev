package com.nl2sql.sql.pipeline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SqlQueryPipeline {
    final List<SqlQueryPipelineStep> steps;

    public String execute(String question){
        var sqlQueryPipelineContext = SqlQueryPipelineContext.builder().question(question).build();
        steps.stream().forEach(step->step.process(sqlQueryPipelineContext));
        return sqlQueryPipelineContext.getSqlResult();

    }

}
