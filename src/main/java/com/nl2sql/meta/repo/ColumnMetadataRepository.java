package com.nl2sql.meta.repo;

import com.nl2sql.schema.model.ColumnMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnMetadataRepository extends JpaRepository<ColumnMetadataEntity, Long> {
}
