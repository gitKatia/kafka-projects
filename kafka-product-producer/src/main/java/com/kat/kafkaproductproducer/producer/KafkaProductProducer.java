package com.kat.kafkaproductproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaproductproducer.config.TopicsProperties;
import com.kat.kafkaproductproducer.model.Product;
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
public class KafkaProductProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() throws JsonProcessingException {
       Product product = Product.builder()
                .productId(UUID.randomUUID().toString())
               .productName(UUID.randomUUID().toString())
               .addedOn(LocalDate.now())
               .build();
        String json = objectMapper.writeValueAsString(product);
        String topicName = topicsProperties.getProductTopic();
        log.info("Sending message {} to topic {}", json, topicName);
        kafkaTemplate.send(topicName, product.getProductId(), json);
    }
}
