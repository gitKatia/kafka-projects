package com.kat.kafkaorderproducer.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private String item;
    private long quantity;
    private String location;
    private LocalDateTime transactionTime;
}
