package com.kat.kafkaitemorderproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ItemOrder {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("order_name")
    private String itemName;
    private long quantity;
    @JsonProperty("order_data")
    private LocalDate orderDate;
}
