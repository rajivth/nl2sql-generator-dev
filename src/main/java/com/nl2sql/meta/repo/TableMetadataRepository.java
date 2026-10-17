package com.nl2sql.meta.repo;

import com.nl2sql.schema.model.TableMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableMetadataRepository extends JpaRepository<TableMetadataEntity, Long> {
}
