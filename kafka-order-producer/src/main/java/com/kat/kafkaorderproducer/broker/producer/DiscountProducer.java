package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.DiscountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountProducer {

    private final KafkaTemplate<String, DiscountMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(DiscountMessage discountMessage) {
        kafkaTemplate.send(topicsProperties.getDiscountsTopic(), discountMessage);
    }
}
