package com.nl2sql.pipeline;

import com.nl2sql.vector.repo.ColumnMetadataVectorRepository;
import com.nl2sql.vector.repo.RelationshipMetadataVectorRepository;
import com.nl2sql.vector.repo.TableMetadataVectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(7)
public class VectorStoreStep implements Pipeline {
    final TableMetadataVectorRepository tableVectorRepository;
    final ColumnMetadataVectorRepository columnVectorRepository;
    final RelationshipMetadataVectorRepository relationshipVectorRepository;

    @Override
    public void process(PipelineContext context) {
        log.debug("Processing vector store step-7");
        context.getVectorDocs().forEach(doc -> {
                    Optional.ofNullable(doc.table()).ifPresentOrElse(tableVectorRepository::save,
                            () -> log.debug("No table vector to save"));

                    Optional.ofNullable(doc.columns()).filter(columns ->
                            !CollectionUtils.isEmpty(columns)).ifPresentOrElse(columnVectorRepository::saveAll,
                            () -> log.debug("No column vectors to save"));

                    Optional.ofNullable(doc.relationships()).filter(relationships -> !CollectionUtils.isEmpty(relationships)).
                            ifPresentOrElse(relationshipVectorRepository::saveAll,
                                    () -> log.debug("No relationship vectors to save"));
                }

        );

    }
}
