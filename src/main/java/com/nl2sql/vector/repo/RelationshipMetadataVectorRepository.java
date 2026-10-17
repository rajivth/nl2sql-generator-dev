package com.nl2sql.vector.repo;

import com.nl2sql.vector.model.RelationshipMetadataVectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RelationshipMetadataVectorRepository extends JpaRepository<RelationshipMetadataVectorEntity, Long> {
}
