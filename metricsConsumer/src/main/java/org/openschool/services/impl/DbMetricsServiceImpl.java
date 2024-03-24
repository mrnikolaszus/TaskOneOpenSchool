package org.openschool.services.impl;

import org.openschool.entity.DbMetrics;
import org.openschool.repository.DbMetricsRepository;
import org.openschool.services.DbMetricsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DbMetricsServiceImpl implements DbMetricsService {

    private final DbMetricsRepository dbMetricsRepository;

    public DbMetricsServiceImpl(DbMetricsRepository dbMetricsRepository) {
        this.dbMetricsRepository = dbMetricsRepository;
    }

    @Override
    public DbMetrics getDbMetricsById(Long id) {
        return dbMetricsRepository.findById(id).orElse(null);
    }

    @Override
    public Page<DbMetrics> getAllDbMetrics(int page, int size) {
        return dbMetricsRepository.findAll(PageRequest.of(page, size));
    }
}
