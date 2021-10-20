package com.kat.kafkarebalancedconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaRebalancedConsumer {

    @KafkaListener(topics = "${kafka-rebalanced-consumer.rebalanced-topic}", concurrency = "3")
    public void processMessage(ConsumerRecord<String, String> message) {
        log.info("Partition : {}, Offset : {}, Message : {}", message.partition(), message.offset(), message.value());
    }
}
