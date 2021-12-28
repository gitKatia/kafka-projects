package com.kat.kafkastreamsone.broker.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRewardMessage {

    private String orderNumber;
    private String itemName;
    private String orderLocation;
    private double price;
    private int quantity;
    private LocalDateTime orderDateTime;
}
