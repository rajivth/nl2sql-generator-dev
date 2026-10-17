package com.nl2sql.schema.metadata.dto;

import java.util.Map;

public record BusinessMetadataDocument(
        String type,
        String schemaName,
        String tableName,
        String columnName,
        String businessName,
        String content,
        Map<String, Object> metadata
) {
}
