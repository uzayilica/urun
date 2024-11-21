package com.uzay.urun.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, String message) {
        kafkaTemplate.send("ucakbilet", key, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Mesaj başarıyla gönderildi: {} - Partition: {}",
                                message, result.getRecordMetadata().partition());
                    } else {
                        logger.error("Mesaj gönderilemedi: {}", ex.getMessage());
                    }
                });
    }
}