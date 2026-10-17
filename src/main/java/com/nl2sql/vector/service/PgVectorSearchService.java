package com.nl2sql.vector.service;

import com.nl2sql.vector.embedding.EmbeddingService;
import com.nl2sql.vector.model.TableMetadataVectorEntity;
import com.nl2sql.vector.model.VectorSearchResponse;
import com.nl2sql.vector.repo.VectorCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PgVectorSearchService implements VectorSearchService {
    private final VectorCustomRepository repository;
    private final EmbeddingService embeddingService;

    @Override
    public VectorSearchResponse search(String question, int topK) {
        var emFloats = embeddingService.fetch(question);
        String vector = Arrays.toString(emFloats);

        List<TableMetadataVectorEntity> tables = repository.searchTable(vector, topK);

        if (tables.isEmpty()) {
            log.debug("Empty table data retrieved");
            return VectorSearchResponse.builder()
                    .tables(List.of())
                    .columns(List.of())
                    .relations(List.of())
                    .build();
        }

        var tableNames = tables.stream()
                .map(TableMetadataVectorEntity::getTableName)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (tableNames.isEmpty()) {
            log.debug("No table names found");
            return VectorSearchResponse.builder()
                    .tables(tables)
                    .columns(List.of())
                    .relations(List.of())
                    .build();
        }

        var columns = repository.searchColumn(vector, tableNames, topK);
        var relations = repository.searchRelation(tableNames);
        return VectorSearchResponse.builder().tables(tables).columns(columns).relations(relations).build();
    }
}
