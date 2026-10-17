package com.nl2sql.sql.intent.model;

import com.nl2sql.vector.model.ColumnMetadataVectorEntity;
import com.nl2sql.vector.model.RelationshipMetadataVectorEntity;
import com.nl2sql.vector.model.TableMetadataVectorEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record SqlGenerationRequest(String question, List<TableMetadataVectorEntity> tables,
                                   List<ColumnMetadataVectorEntity> columns,
                                   List<RelationshipMetadataVectorEntity> relations) {
}
