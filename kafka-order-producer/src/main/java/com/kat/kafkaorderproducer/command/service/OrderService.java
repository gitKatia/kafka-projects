package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.OrderAction;
import com.kat.kafkaorderproducer.controller.request.OrderRequest;
import com.kat.kafkaorderproducer.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderAction orderAction;

    public String saveOrder(OrderRequest orderRequest) {
        Order order = orderAction.convertToOrder(orderRequest);
        orderAction.saveToDatabase(order);
        order.getItems().forEach(orderAction::publishToKafka);
        return order.getOrderNumber();
    }
}
