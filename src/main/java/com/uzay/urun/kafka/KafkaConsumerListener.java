package com.uzay.urun.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    // Her consumer'ı belirli bir partition'a atayalım
    @KafkaListener(topicPartitions = @TopicPartition(
            topic = "ucakbilet",
            partitions = "0"),  // partition 0'ı dinle
            groupId = "group-1"
    )
    public void consumeMessage1(String message) {
        log.info("Consumer 1 (Partition 0) mesaj alındı: {}", message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(
            topic = "ucakbilet",
            partitions = "1"),  // partition 1'i dinle
            groupId = "group-1"
    )
    public void consumeMessage2(String message) {
        log.info("Consumer 2 (Partition 1) mesaj alındı: {}", message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(
            topic = "ucakbilet",
            partitions = "2"),  // partition 2'yi dinle
            groupId = "group-1"
    )
    public void consumeMessage3(String message) {
        log.info("Consumer 3 (Partition 2) mesaj alındı: {}", message);
    }
}
