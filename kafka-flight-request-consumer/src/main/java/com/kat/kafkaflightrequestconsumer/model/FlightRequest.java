package com.kat.kafkaflightrequestconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightRequest {
    @JsonProperty("request_id")
    private String requestId;
    private String from;
    private String to;
    @JsonProperty("departure_date")
    private LocalDate departureDate;
}
