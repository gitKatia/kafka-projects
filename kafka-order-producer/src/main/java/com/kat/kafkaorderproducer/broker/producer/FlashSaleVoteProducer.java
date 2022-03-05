package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.FlashSaleVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlashSaleVoteProducer {
    private final KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(FlashSaleVoteMessage flashSaleVoteMessage) {
        kafkaTemplate.send(topicsProperties.getFlashSaleVotesTopic(), flashSaleVoteMessage);
    }
}
