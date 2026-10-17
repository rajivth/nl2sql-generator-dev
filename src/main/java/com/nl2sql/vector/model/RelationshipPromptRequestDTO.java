package com.nl2sql.vector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nl2sql.schema.model.RelationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RelationshipPromptRequestDTO(
        String sourceSchema,
        String sourceTable,
        String sourceColumn,
        String targetSchema,
        String targetTable,
        String targetColumn,
        RelationType relationshipType
) {
}
