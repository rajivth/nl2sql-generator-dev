package com.nl2sql.api.controller;

import com.nl2sql.api.request.QueryRequest;
import com.nl2sql.api.response.QueryResponse;
import com.nl2sql.sql.pipeline.SqlQueryPipeline;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sql")
@RequiredArgsConstructor
@Slf4j
public class SqlController {
   /* private final SqlGenerationService sqlGenerationService;*/
    private final SqlQueryPipeline sqlQueryPipeline;

    @PostMapping("/generate")
    public ResponseEntity<QueryResponse> generateSql(@Valid @RequestBody QueryRequest request) {
       /* QueryResponse response = sqlGenerationService.generateSql(request.question());*/
        var result = sqlQueryPipeline.execute(request.question());
        log.info("Analyze {}", result);
        return ResponseEntity.ok(new QueryResponse(request.question(),result));
    }


}
