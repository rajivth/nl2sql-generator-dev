package com.nl2sql.vector.repo;

import com.nl2sql.vector.model.TableMetadataVectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableMetadataVectorRepository  extends JpaRepository<TableMetadataVectorEntity, Long> {
}
