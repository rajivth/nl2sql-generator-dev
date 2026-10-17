package com.nl2sql.vector.model;

import com.nl2sql.schema.model.RelationType;
import com.nl2sql.vector.embedding.EmbeddableVector;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@Table(name = "relationship_metadata_vector")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipMetadataVectorEntity implements EmbeddableVector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "schema_name", length = 128)
    private String schemaName;

    @Column(name = "table_name", length = 128)
    private String tableName;

    @Column(name = "from_table", length = 128)
    private String sourceTable;

    @Column(name = "from_column", length = 128)
    private String sourceColumn;

    @Column(name = "to_table", length = 128)
    private String targetTable;

    @Column(name = "to_column",length = 128)
    private String targetColumn;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type", length = 50)
    private RelationType relationshipType;

    @Column(name = "object_type")
    private String objectType;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Column(name = "embedding", columnDefinition = "vector(768)")
    private float[] embedding;

    @Column(name = "business_name", length = 255)
    private String businessName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "business_keywords", columnDefinition = "jsonb")
    private List<String> businessKeywords;
}
