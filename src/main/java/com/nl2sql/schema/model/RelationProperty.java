package com.nl2sql.schema.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record RelationProperty(String sourceTable,
                               String sourceColumn,
                               String targetTable,
                               String targetColumn,
                               RelationType relationshipType,
                               String businessName,
                               String description,
                               List<String> businessKeywords) {
}
