package com.kat.kafkaproductconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaProductConsumer {
    void processMessage(ConsumerRecord<String, String> message);
}
