package com.nl2sql.vector.model;

import com.nl2sql.vector.embedding.EmbeddableVector;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@Table(name = "table_metadata_vector",
        schema = "public", uniqueConstraints =
        {@UniqueConstraint(name = "uk_table_metadata",
                columnNames = {"schemaName",
                        "tableName"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableMetadataVectorEntity implements EmbeddableVector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "schema_name", length = 128)
    private String schemaName;

    @Column(name = "table_name", length = 128)
    private String tableName;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "business_keywords", columnDefinition = "jsonb")
    private List<String> businessKeywords;

    @Column(name = "object_type")
    private String objectType;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(name = "embedding", columnDefinition = "vector(768)")
    private float[] embedding;
}
