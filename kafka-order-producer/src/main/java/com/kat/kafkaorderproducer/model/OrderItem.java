package com.kat.kafkaorderproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@Builder
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private long orderItemId;

    private String itemName;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;
}
