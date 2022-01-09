package com.kat.ordersmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {
    private String creditCardNumber;
    private String itemName;
    private LocalDateTime orderDateTime;
    private String orderLocation;
    private String orderNumber;
    private double price;
    private int quantity;

    public OrderMessage copy() {
        OrderMessage copy = new OrderMessage();
        copy.setCreditCardNumber(this.creditCardNumber);
        copy.setItemName(this.itemName);
        copy.setOrderDateTime(this.orderDateTime);
        copy.setOrderLocation(this.orderLocation);
        copy.setOrderNumber(this.orderNumber);
        copy.setPrice(this.price);
        copy.setQuantity(this.quantity);

        return copy;
    }
}
