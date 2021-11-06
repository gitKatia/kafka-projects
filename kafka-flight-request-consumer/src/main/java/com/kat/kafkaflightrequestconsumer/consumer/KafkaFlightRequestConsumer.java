package com.kat.kafkaflightrequestconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkaflightrequestconsumer.model.FlightRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaFlightRequestConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka-flight-request-consumer.flight-request-topic}", containerFactory = "flightRequestRetryContainerFactory")
    public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
        FlightRequest flightRequest = objectMapper.readValue(message, FlightRequest.class);
        if("ABC".equals(flightRequest.getTo())) {
            throw new RuntimeException("API call failure.");
        }
        log.info("Processing flight request {}", flightRequest);
    }
}
