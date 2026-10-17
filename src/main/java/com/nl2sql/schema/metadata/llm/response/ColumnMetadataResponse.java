package com.nl2sql.schema.metadata.llm.response;

import com.nl2sql.schema.metadata.dto.BusinessMetadataColumn;
import lombok.Builder;

import java.util.List;

@Builder
public record ColumnMetadataResponse(
        String schemaName,
        String tableName,
        List<BusinessMetadataColumn> columns
) {
}
