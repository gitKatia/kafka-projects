package com.kat.kafkabookingproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkabookingproducer.config.TopicsProperties;
import com.kat.kafkabookingproducer.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaBookingProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final TopicsProperties topicsProperties;

    public void publishBooking(Booking booking) {
        log.info("About to publish booking {}", booking);
        try {
            String bookingAsJsonString = objectMapper.writeValueAsString(booking);
            kafkaTemplate.send(topicsProperties.getBookingTopic(), booking.getBookingId(), bookingAsJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while converting object. Message: "  + e.getMessage());
        }
    }
}
