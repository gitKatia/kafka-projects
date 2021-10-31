package com.kat.kafkaitemorderproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaitemorderproducer.config.TopicsProperties;
import com.kat.kafkaitemorderproducer.model.ItemOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaItemOrderProducer implements ItemOrderPublisher{
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(ItemOrder itemOrder){
        log.info("About to publish item order {}", itemOrder);
        try {
            String itemOrderAsJsonString = objectMapper.writeValueAsString(itemOrder);
            kafkaTemplate.send(topicsProperties.getItemOrderTopic(), itemOrder.getOrderId(), itemOrderAsJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while converting object. Message: "  + e.getMessage());
        }
    }
}
