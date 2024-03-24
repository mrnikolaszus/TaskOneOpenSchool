package org.openschool.services;

import org.openschool.entity.DbMetrics;
import org.springframework.data.domain.Page;

public interface DbMetricsService {

    DbMetrics getDbMetricsById(Long id);
    Page<DbMetrics> getAllDbMetrics(int page, int size);
}
