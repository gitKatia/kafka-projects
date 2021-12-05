package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.OrderService;
import com.kat.kafkaorderproducer.controller.request.OrderRequest;
import com.kat.kafkaorderproducer.controller.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        String orderNumber = orderService.saveOrder(orderRequest);
        OrderResponse orderResponse = new OrderResponse(orderNumber);
        return ResponseEntity.ok().body(orderResponse);
    }
}
