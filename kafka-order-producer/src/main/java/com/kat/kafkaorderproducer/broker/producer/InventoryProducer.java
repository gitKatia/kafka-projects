package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryProducer {
    private final KafkaTemplate<String, InventoryMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(InventoryMessage inventoryMessage) {
        kafkaTemplate.send(topicsProperties.getInventoryTopic(), inventoryMessage.getItem(), inventoryMessage);
    }
}
