package com.kat.kafkabookingproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Booking {
    @JsonProperty("booking_id")
    private String bookingId;
    @JsonProperty("check_in")
    private LocalDate checkIn;
    @JsonProperty("check_out")
    private LocalDate checkout;
    private int adults;
    private int children;
    private int rooms;
}
