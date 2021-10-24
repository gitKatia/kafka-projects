package com.kat.kafkapersonproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkapersonproducer.config.TopicsProperties;
import com.kat.kafkapersonproducer.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPersonProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() throws JsonProcessingException {
        Person person = Person.builder()
                .personId(UUID.randomUUID().toString())
                .age(new Random().nextInt(100))
                .build();
        String topicName = topicsProperties.getPersonTopic();
        String json = objectMapper.writeValueAsString(person);
        log.info("Sending message {} to kafka topic {}", person, topicName);
        kafkaTemplate.send(topicName, person.getPersonId(), json);
    }
}
