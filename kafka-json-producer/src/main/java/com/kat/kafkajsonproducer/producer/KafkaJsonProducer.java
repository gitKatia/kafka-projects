package com.kat.kafkajsonproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkajsonproducer.config.TopicsProperties;
import com.kat.kafkajsonproducer.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() throws JsonProcessingException {
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .joinDate(LocalDate.now())
                .build();
        String json = objectMapper.writeValueAsString(user);
        String topicName = topicsProperties.getUserTopic();
        log.info("Sending message {} to topic {}", json, topicName);
        kafkaTemplate.send(topicName, json);
    }
}
