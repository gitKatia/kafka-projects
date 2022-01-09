package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackProducer {

    private final KafkaTemplate<String, FeedbackMessage> kafkaTemplate;
    private final TopicsProperties topicProperties;

    public void publish(FeedbackMessage message) {
        kafkaTemplate.send(topicProperties.getFeedbackTopic(), message);
    }

}
