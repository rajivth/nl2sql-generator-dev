package com.nl2sql.vector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TablePromptRequestDTO(String schemaName, String tableName, String businessName, String description) {
}
