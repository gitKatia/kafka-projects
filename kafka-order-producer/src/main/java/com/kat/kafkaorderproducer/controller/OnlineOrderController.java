package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.OnlineOrderService;
import com.kat.kafkaorderproducer.controller.request.OnlineOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-orders")
@RequiredArgsConstructor
public class OnlineOrderController {
    private final OnlineOrderService onlineOrderService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createOnlineOrder(@RequestBody OnlineOrderRequest onlineOrderRequest) {
        onlineOrderService.publishOrderToKafka(onlineOrderRequest);
        return ResponseEntity.ok().body("Saved online order " + onlineOrderRequest.getOnlineOrderNumber());
    }
}
