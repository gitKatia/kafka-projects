package com.kat.ordersmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMessage {
    private String item;
    private String location;
    private long quantity;
    private String type;
    private LocalDateTime transactionTime;
}
