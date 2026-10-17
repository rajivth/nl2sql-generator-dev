package com.nl2sql.meta.repo;

import com.nl2sql.schema.model.RelationshipMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipMetadataRepository extends JpaRepository<RelationshipMetadataEntity, Long> {
}
