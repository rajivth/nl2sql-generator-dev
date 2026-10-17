package com.nl2sql.schema.metadata.llm.response;

import com.nl2sql.schema.model.RelationProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record RelationshipMetadataResponse(
        String schemaName,
        String tableName,
        List<RelationProperty> relationInfos
) {
}
