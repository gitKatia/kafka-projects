package com.kat.kafkaconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasicKafkaConsumer {

    @KafkaListener(topics = "${kafka-consumer.first-topic}")
    public void consumeMessageFromFirstTopic(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received message {} on topic {}", message, topic);
    }

    @KafkaListener(topics = "${kafka-consumer.second-topic}")
    public void consumeMessageFromSecondTopic(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received message {} on topic {}", message, topic);
    }
}
