package com.nl2sql.schema.model;

import lombok.Builder;

import java.util.List;

@Builder
public record TableProperty(String tableName, List<ColumnProperty> columns, List<RelationProperty> relation) {
}
