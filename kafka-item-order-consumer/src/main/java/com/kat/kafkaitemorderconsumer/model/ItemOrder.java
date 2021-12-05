package com.kat.kafkaitemorderconsumer.model;

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
public class ItemOrder {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("order_name")
    private String itemName;
    private long quantity;
    @JsonProperty("order_data")
    private LocalDate orderDate;
}
