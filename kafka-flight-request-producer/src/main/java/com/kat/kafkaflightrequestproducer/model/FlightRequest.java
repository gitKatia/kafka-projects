package com.kat.kafkaflightrequestproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class FlightRequest {
    @JsonProperty("request_id")
    private String requestId;
    private String from;
    private String to;
    @JsonProperty("departure_date")
    private LocalDate departureDate;
}
