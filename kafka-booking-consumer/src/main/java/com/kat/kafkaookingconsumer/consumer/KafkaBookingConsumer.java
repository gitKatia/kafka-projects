package com.kat.kafkaookingconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaookingconsumer.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaBookingConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka-booking-consumer.booking-topic}", containerFactory = "bookingDltContainerFactory")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
        Booking booking = objectMapper.readValue(message, Booking.class);
        long total = booking.getAdults() + booking.getChildren();
        if(booking.getRooms() > total) {
            throw new RuntimeException("Number of rooms is GT number of children and adults.");
        }
        log.info("Processing booking {}", booking);
    }

    @KafkaListener(topics = "${kafka-booking-consumer.booking-topic-dlt}")
    public void consumeFromDlt(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
    }
}
