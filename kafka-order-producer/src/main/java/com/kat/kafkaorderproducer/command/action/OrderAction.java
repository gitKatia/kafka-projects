package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.OrderProducer;
import com.kat.kafkaorderproducer.controller.request.OrderItemRequest;
import com.kat.kafkaorderproducer.controller.request.OrderRequest;
import com.kat.kafkaorderproducer.model.Order;
import com.kat.kafkaorderproducer.model.OrderItem;
import com.kat.kafkaorderproducer.repository.OrderRepository;
import com.kat.ordersmodel.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderAction {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public Order convertToOrder(OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
        Order order = Order.builder()
                .creditCardNumber(orderRequest.getCreditCardNumber())
                .orderDateTime(LocalDateTime.now())
                .orderNumber(UUID.randomUUID().toString())
                .orderLocation(orderRequest.getOrderLocation())
                .items(orderItems)
                .build();
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        return order;
    }

    private OrderItem convertToOrderItem(OrderItemRequest orderItemRequest) {
        return OrderItem.builder()
                .itemName(orderItemRequest.getItemName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    public void publishToKafka(OrderItem orderItem) {
        OrderMessage orderMessage = OrderMessage.builder()
                .creditCardNumber(orderItem.getOrder().getCreditCardNumber())
                .itemName(orderItem.getItemName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .orderDateTime(orderItem.getOrder().getOrderDateTime())
                .orderLocation(orderItem.getOrder().getOrderLocation())
                .orderNumber(orderItem.getOrder().getOrderNumber())
                .build();
        orderProducer.publish(orderMessage);
    }

    public void saveToDatabase(Order order) {
        orderRepository.save(order);
    }
}
