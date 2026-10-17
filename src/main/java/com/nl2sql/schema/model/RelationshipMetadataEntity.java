package com.nl2sql.schema.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "relationship_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipMetadataEntity {

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
    private RelationType type;
}
