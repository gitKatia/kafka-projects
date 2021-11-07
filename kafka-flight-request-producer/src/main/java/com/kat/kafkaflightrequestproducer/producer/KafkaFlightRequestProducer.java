package com.kat.kafkaflightrequestproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaflightrequestproducer.config.TopicsProperties;
import com.kat.kafkaflightrequestproducer.model.FlightRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaFlightRequestProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    public void publishFlightRequest(FlightRequest flightRequest) {
        log.info("About to publish flight request {}", flightRequest);
        try {
            String flightRequestAsJsonString = objectMapper.writeValueAsString(flightRequest);
            kafkaTemplate.send(topicsProperties.getFlightRequestTopic(), flightRequest.getRequestId(), flightRequestAsJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while converting object. Message: "  + e.getMessage());
        }
    }
}
