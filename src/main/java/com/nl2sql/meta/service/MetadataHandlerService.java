package com.nl2sql.meta.service;

import com.nl2sql.common.JacksonConverter;
import com.nl2sql.meta.repo.ColumnMetadataRepository;
import com.nl2sql.meta.repo.RelationshipMetadataRepository;
import com.nl2sql.meta.repo.TableMetadataRepository;
import com.nl2sql.schema.metadata.llm.response.BusinessMetaResponse;
import com.nl2sql.schema.model.ColumnMetadataEntity;
import com.nl2sql.schema.model.RelationshipMetadataEntity;
import com.nl2sql.schema.model.TableMetadataEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MetadataHandlerService {
    private final JacksonConverter jacksonConverter;
    private final TableMetadataRepository tableMetadataRepository;
    private final ColumnMetadataRepository columnMetadataRepository;
    private final RelationshipMetadataRepository relationshipMetadataRepository;

    public void saveMeta(BusinessMetaResponse businessMetaResponse) {
        var tableMetadata = businessMetaResponse.businessMetadata();
        var columnMetadata = businessMetaResponse.columnMetadata();
        var relationMetadata = businessMetaResponse.relationshipMetadata();

        var tableMetadataEntity = jacksonConverter.convertSourceToTarget(tableMetadata, TableMetadataEntity.class);
        var columnMetadataEntities = columnMetadata.columns().stream().
                map(metadataColumn -> {
                    var columnMetadataEntity = jacksonConverter.convertSourceToTarget(metadataColumn, ColumnMetadataEntity.class);
                    columnMetadataEntity.setSchemaName(tableMetadataEntity.getSchemaName());
                    columnMetadataEntity.setTableName(tableMetadataEntity.getTableName());
                    return columnMetadataEntity;
                }).toList();

        var relationshipMetadataEntities = relationMetadata.relationInfos().stream().
                map(relationInfo -> {
                    var relationshipMetadataEntity = jacksonConverter.convertSourceToTarget(relationInfo, RelationshipMetadataEntity.class);
                    relationshipMetadataEntity.setSchemaName(tableMetadataEntity.getSchemaName());
                    relationshipMetadataEntity.setTableName(tableMetadataEntity.getTableName());
                    return relationshipMetadataEntity;
                }).toList();

        tableMetadataRepository.save(tableMetadataEntity);
        columnMetadataRepository.saveAll(columnMetadataEntities);
        relationshipMetadataRepository.saveAll(relationshipMetadataEntities);
    }
}
