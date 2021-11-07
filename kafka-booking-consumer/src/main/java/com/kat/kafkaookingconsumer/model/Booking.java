package com.kat.kafkaookingconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
