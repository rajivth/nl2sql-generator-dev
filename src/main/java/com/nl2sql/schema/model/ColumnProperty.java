package com.nl2sql.schema.model;

import lombok.Builder;

@Builder
public record ColumnProperty(String columnName, String dataType, boolean nullable, boolean primaryKey) {
}
