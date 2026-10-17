package com.nl2sql.schema.metadata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BusinessMetadataColumn(
        String columnName,
        String businessName,
        String dataType,
        String description,
        List<String> businessKeywords
) {
}
