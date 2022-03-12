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
public class OnlinePaymentMessage {
    private String onlineOrderNumber;
    private LocalDateTime paymentDateTime;
    private String paymentMethod;
    private String paymentNumber;
}
