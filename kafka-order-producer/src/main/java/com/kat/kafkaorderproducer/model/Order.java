package com.kat.kafkaorderproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private long orderId;

    @Column(nullable = false, length = 20)
    private String creditCardNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @Column(nullable = false, length = 200)
    private String orderLocation;

    @Column(nullable = false, length = 200)
    private String orderNumber;
}
