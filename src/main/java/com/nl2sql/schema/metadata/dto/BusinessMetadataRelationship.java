package com.nl2sql.schema.metadata.dto;

import java.util.List;

public record BusinessMetadataRelationship(
        String schemaName,
        String tableName,
        String sourceTable,
        String sourceColumn,
        String targetTable,
        String targetColumn,
        String relationshipType,
        String businessName,
        String description,
        List<String> businessKeywords
) {
}
