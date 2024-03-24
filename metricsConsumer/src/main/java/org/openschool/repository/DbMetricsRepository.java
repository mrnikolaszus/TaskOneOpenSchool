package org.openschool.repository;

import org.openschool.entity.DbMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbMetricsRepository extends JpaRepository<DbMetrics, Long> {
}
