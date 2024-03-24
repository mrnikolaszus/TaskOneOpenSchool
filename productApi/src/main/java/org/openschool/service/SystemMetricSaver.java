package org.openschool.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.openschool.entity.Metrics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class SystemMetricSaver {


    private final MeterRegistry meterRegistry;
    private final KafkaTemplate<String, Metrics> kafkaTemplate;

    public SystemMetricSaver(MeterRegistry meterRegistry, KafkaTemplate<String, Metrics> kafkaTemplate) {
        this.meterRegistry = meterRegistry;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void saveSystemMetrics() {
        saveMetric("system.cpu.usage", "System CPU Usage");
        saveMetric("jvm.memory.used", "JVM Memory Used");
    }

    private void saveMetric(String metricName, String metricDescription) {
        Double value = getMetricValue(metricName);

        if (value != null) {
            Metrics metric = new Metrics();
            metric.setName(metricDescription);
            metric.setValue(value);

            kafkaTemplate.send("metrics", metric);
        }

    }

    private Double getMetricValue(String metricName) {
        Gauge gauge = meterRegistry.find(metricName).gauge();
        return gauge != null ? gauge.value() : null;
    }
}