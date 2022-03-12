package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.OnlineOrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlineOrderProducer {
    private final KafkaTemplate<String, OnlineOrderMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(OnlineOrderMessage onlineOrderMessage) {
        kafkaTemplate.send(topicsProperties.getOnlineOrderTopic(), onlineOrderMessage.getOnlineOrderNumber(), onlineOrderMessage);
    }
}
