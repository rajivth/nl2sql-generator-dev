package com.nl2sql.schema.metadata.prompt;

import com.nl2sql.schema.model.ColumnProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ColumnPayloadRequestPrompt(
        String schemaName,
        String tableName,
        List<ColumnProperty> columns
) {
}
