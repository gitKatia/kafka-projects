package com.kat.kafkakeyconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaKeyConsumer {

    @KafkaListener(topics = "${kafka-key-consumer.third-topic}", concurrency = "3")
    public void consumeMessage(ConsumerRecord<String, String> message) {
        log.info("Received message {} with Key {} on partition {} of topic {}", message.value(), message.key(), message.partition(),
                message.topic());
    }
}
