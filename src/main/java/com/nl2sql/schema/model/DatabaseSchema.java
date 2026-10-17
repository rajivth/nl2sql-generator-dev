package com.nl2sql.schema.model;

import lombok.Builder;

@Builder
public record DatabaseSchema(String schemaName, TableProperty tableInfo) {
}
