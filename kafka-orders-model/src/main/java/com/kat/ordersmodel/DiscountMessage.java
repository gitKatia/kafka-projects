package com.kat.ordersmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountMessage {
    private String discountCode;
    private double discountPercentage;
}
