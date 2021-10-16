package com.kat.kafkaproducer.producer;

import com.kat.kafkaproducer.config.TopicsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void sendRandomInt() {
        Random random = new Random();
        String data = String.format("New random int %d", random.nextInt());
        String topicName = topicsProperties.getFirstTopic();
        log.info("Sending message with data {} to topic {}", data, topicName);
        kafkaTemplate.send(topicName, data);
    }

    @Scheduled(fixedRate = 1000)
    public void sendDate() {
        String topicName = topicsProperties.getSecondTopic();
        String data = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        log.info("Sending message with data {} to topic {}", data, topicName);
        kafkaTemplate.send(topicName, data);
    }
}
