package com.kat.kafkaitemorderconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaitemorderconsumer.model.ItemOrder;
import com.kat.kafkaitemorderconsumer.model.exception.ItemOrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaItemOrderConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka-item-order-consumer.item-order-topic}", errorHandler = "itemOrderErrorHandler")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
        ItemOrder itemOrder = objectMapper.readValue(message, ItemOrder.class);
        if(itemOrder.getQuantity() <= 0) {
            throw new ItemOrderException(format("Item order with id %s has a non-positive quantity %d", itemOrder.getOrderId(),
                    itemOrder.getQuantity()));
        }
    }
}
