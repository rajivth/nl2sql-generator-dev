package com.nl2sql.vector.model;

import lombok.Builder;

import java.util.List;

@Builder
public record VectorSearchResponse(List<TableMetadataVectorEntity> tables,List<ColumnMetadataVectorEntity> columns,List<RelationshipMetadataVectorEntity> relations) {
}
