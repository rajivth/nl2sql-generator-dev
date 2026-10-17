package com.nl2sql.vector.repo;

import com.nl2sql.vector.model.ColumnMetadataVectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColumnMetadataVectorRepository extends JpaRepository<ColumnMetadataVectorEntity, Long> {
}
