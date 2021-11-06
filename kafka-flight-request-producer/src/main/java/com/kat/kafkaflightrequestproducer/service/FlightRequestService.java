package com.kat.kafkaflightrequestproducer.service;

import com.kat.kafkaflightrequestproducer.model.FlightRequest;
import com.kat.kafkaflightrequestproducer.producer.KafkaFlightRequestProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightRequestService {

    private final KafkaFlightRequestProducer kafkaFlightRequestProducer;

    private List<FlightRequest> getFlightRequests() {
        FlightRequest flightRequest1 = FlightRequest.builder()
                .from(UUID.randomUUID().toString())
                .to(UUID.randomUUID().toString())
                .departureDate(LocalDate.now())
                .build();
        FlightRequest flightRequest2 = FlightRequest.builder()
                .from(UUID.randomUUID().toString())
                .to("ABC")
                .departureDate(LocalDate.now())
                .build();
        return Arrays.asList(flightRequest1, flightRequest2);
    }

    @Scheduled(fixedRate = 3000)
    public void sendFlightRequests() {
        getFlightRequests().forEach(kafkaFlightRequestProducer::publishFlightRequest);
    }
}
