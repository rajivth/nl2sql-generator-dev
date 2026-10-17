package com.nl2sql.vector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ColumnPromptRequestDTO(
        String schemaName,
        String tableName,
        String columnName,
        String dataType,
        String businessName,
        String datType
) {
}
