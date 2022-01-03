package com.kat.kafkastreamsfour.broker.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderPatternMessage {

    private String orderNumber;
    private String itemName;
    private double totalItemAmount;
    private LocalDateTime orderDateTime;
    private String orderLocation;
}
