package com.kat.kafkaorderproducer.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineOrderRequest {
    private String onlineOrderNumber;
    private LocalDateTime orderDateTime;
    private int totalAmount;
    private String username;
}
