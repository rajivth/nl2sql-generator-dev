package com.nl2sql.vector.repo;

import com.nl2sql.vector.model.ColumnMetadataVectorEntity;
import com.nl2sql.vector.model.RelationshipMetadataVectorEntity;
import com.nl2sql.vector.model.TableMetadataVectorEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 *
 * JPQL query finding search
 *
 *    @Query("""
 *         SELECT t
 *         FROM TableMetadataVectorEntity t
 *         ORDER BY cosine_distance(t.embedding, :embedding)
 *     """)
 *     List<TableMetadataVectorEntity> search(
 *             @Param("embedding") float[] embedding,
 *             Pageable pageable
 *     );
 */

@Repository
@RequiredArgsConstructor
public class VectorCustomRepository {

    private final EntityManager entityManager;


    public List<TableMetadataVectorEntity> searchTable(String embedding, int topK) {

        String sql = """
                SELECT *
                FROM public.table_metadata_vector
                ORDER BY embedding <=> CAST(:embedding AS vector)
                LIMIT :topK
                """;

        var query = entityManager.createNativeQuery(sql, TableMetadataVectorEntity.class);

        query.setParameter("embedding", embedding);
        query.setParameter("topK", topK);

        return query.getResultList();
    }

    public List<ColumnMetadataVectorEntity> searchColumn(String embedding, List<String> tableNames, int topK) {
        String sql = """
                SELECT *
                FROM public.column_metadata_vector
                WHERE LOWER(table_name)  IN (:tableNames)
                ORDER BY embedding <=> CAST(:embedding AS vector)
                LIMIT :topK
                """;

        var query = entityManager.createNativeQuery(sql, ColumnMetadataVectorEntity.class);

        query.setParameter("embedding", embedding);
        query.setParameter("tableNames", tableNames.stream().map(String::toLowerCase).toList());
        query.setParameter("topK", topK);
        return query.getResultList();
    }

    public List<RelationshipMetadataVectorEntity> searchRelation( List<String> tableNames) {
        String sql = """
                SELECT *
                FROM public.relationship_metadata_vector
                WHERE LOWER(table_name)  IN (:tableNames)
                """;

        var query = entityManager.createNativeQuery(sql, RelationshipMetadataVectorEntity.class);
        query.setParameter("tableNames", tableNames.stream().map(String::toLowerCase).toList());
        return query.getResultList();
    }
}