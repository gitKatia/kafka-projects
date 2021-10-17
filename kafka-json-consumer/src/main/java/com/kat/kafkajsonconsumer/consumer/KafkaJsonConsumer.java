package com.kat.kafkajsonconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkajsonconsumer.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaJsonConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka-json-consumer.user-topic}")
    public void consumeMessage(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName) throws JsonProcessingException {
        User user = objectMapper.readValue(message, User.class);
        log.info("Processing user {} on topic {}", user, topicName);
    }
}
