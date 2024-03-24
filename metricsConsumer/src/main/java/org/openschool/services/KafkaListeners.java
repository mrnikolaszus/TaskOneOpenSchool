package org.openschool.services;

import org.openschool.entity.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@Slf4j
public class KafkaListeners {
    @KafkaListener(topics = "metrics",
    groupId = "groupId")
    void listener(@Payload Metrics metrics){
        log.error("LISTENER RECEIVED: " + metrics + "!!!");
    }
}
