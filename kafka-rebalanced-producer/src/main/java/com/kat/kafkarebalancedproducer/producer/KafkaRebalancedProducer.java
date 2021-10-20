package com.kat.kafkarebalancedproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkarebalancedproducer.config.TopicsProperties;
import com.kat.kafkarebalancedproducer.model.Item;
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
public class KafkaRebalancedProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() throws JsonProcessingException {
        Item item = Item.builder()
                .itemId(UUID.randomUUID().toString())
                .itemName(UUID.randomUUID().toString())
                .createdOn(LocalDate.now())
                .build();
        String topicName = topicsProperties.getRebalancedTopic();
        String json = objectMapper.writeValueAsString(item);
        log.info("Sending message {} to kafka topic {}", item, topicName);
        kafkaTemplate.send(topicName, item.getItemId(), json);
    }
}
