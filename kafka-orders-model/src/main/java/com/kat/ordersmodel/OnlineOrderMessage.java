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
public class OnlineOrderMessage {
    private String onlineOrderNumber;
    private LocalDateTime orderDateTime;
    private int totalAmount;
    private String username;
}
