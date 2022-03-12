package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.OnlinePaymentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlinePaymentProducer {
    private final KafkaTemplate<String, OnlinePaymentMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(OnlinePaymentMessage onlinePaymentMessage) {
        kafkaTemplate.send(topicsProperties.getOnlinePaymentTopic(), onlinePaymentMessage.getOnlineOrderNumber(), onlinePaymentMessage);
    }
}
