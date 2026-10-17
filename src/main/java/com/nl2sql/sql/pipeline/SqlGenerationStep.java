package com.nl2sql.sql.pipeline;

import com.nl2sql.sql.intent.analyzer.IntentAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(3)
public class SqlGenerationStep implements SqlQueryPipelineStep {
    final IntentAnalyzer intentAnalyzer;

    @Override
    public void process(SqlQueryPipelineContext context) {
        log.debug("SqlGenerationStep process Step-3");
        if (context.getSqlResult() != null) {
            return;
        }
        var sqlQueryResp = intentAnalyzer.generate(context.getPrompt());
        log.debug("Query Response Printed:: \n {}", sqlQueryResp);
        context.setSqlResult(sqlQueryResp);
    }
}
