package com.nl2sql.vector.model;

import lombok.Builder;

import java.util.List;

@Builder
public record BusinessMetaDataVectorDoc(TableMetadataVectorEntity table, List<ColumnMetadataVectorEntity> columns, List<RelationshipMetadataVectorEntity> relationships) {
}
