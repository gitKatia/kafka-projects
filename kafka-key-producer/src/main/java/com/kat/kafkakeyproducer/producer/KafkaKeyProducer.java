package com.kat.kafkakeyproducer.producer;

import com.kat.kafkakeyproducer.config.TopicsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaKeyProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void send(String key, String data) {
        String topicName = topicsProperties.getThirdTopic();
        log.info("Publishing message {} to topic {}", data, topicName);
        kafkaTemplate.send(topicName, key, data);
    }
}
