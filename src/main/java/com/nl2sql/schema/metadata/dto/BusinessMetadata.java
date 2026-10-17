package com.nl2sql.schema.metadata.dto;

import java.util.List;

public record BusinessMetadata(
        String schemaName,
        String tableName,
        String businessName,
        String description,
        List<String> businessKeywords
) {
}
