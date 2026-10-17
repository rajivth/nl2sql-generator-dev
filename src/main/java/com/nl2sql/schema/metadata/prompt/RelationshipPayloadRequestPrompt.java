package com.nl2sql.schema.metadata.prompt;

import com.nl2sql.schema.model.RelationProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record RelationshipPayloadRequestPrompt(
        String schemaName,
        String tableName,
        List<RelationProperty> relationInfos
) {
}
