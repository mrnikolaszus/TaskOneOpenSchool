package org.openschool.services;

import org.openschool.entity.DbMetrics;
import org.openschool.entity.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.openschool.repository.DbMetricsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class KafkaListeners {

    private final DbMetricsRepository dbMetricsRepository;

    public KafkaListeners(DbMetricsRepository dbMetricsRepository) {
        this.dbMetricsRepository = dbMetricsRepository;
    }

    @KafkaListener(topics = "metrics", groupId = "groupId")
    void listener(@Payload Metrics metrics) {
        var dbMetrics = new DbMetrics();
        dbMetrics.setName(metrics.getName());
        dbMetrics.setValue(metrics.getValue());
        dbMetrics.setCreatedAt(new Date());
        dbMetricsRepository.save(dbMetrics);

    }
}
