package com.nl2sql.schema.metadata.llm.response;

import com.nl2sql.schema.metadata.dto.BusinessMetadata;
import lombok.Builder;

@Builder
public record BusinessMetaResponse(
    BusinessMetadata businessMetadata,
    ColumnMetadataResponse columnMetadata,
    RelationshipMetadataResponse relationshipMetadata){
}
