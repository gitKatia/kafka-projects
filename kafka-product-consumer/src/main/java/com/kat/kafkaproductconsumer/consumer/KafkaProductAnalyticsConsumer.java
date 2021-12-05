package com.kat.kafkaproductconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProductAnalyticsConsumer implements KafkaProductConsumer {

    @Override
    @KafkaListener(topics = "${kafka-product-consumer.product-topic}", groupId = "p-analytics-cg")
    public void processMessage(ConsumerRecord<String, String> message) {
        log.info("Partition : {}, Offset : {}, Message : {}", message.partition(), message.offset(), message.value());
    }
}
