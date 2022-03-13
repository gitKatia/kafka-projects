package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.SubscriptionPurchaseService;
import com.kat.kafkaorderproducer.controller.request.SubscriptionPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription-purchases")
@RequiredArgsConstructor
public class SubscriptionPurchaseController {
    private final SubscriptionPurchaseService subscriptionPurchaseService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createUser(@RequestBody SubscriptionPurchaseRequest subscriptionPurchaseRequest) {
        subscriptionPurchaseService.createPurchase(subscriptionPurchaseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription purchase created:" + subscriptionPurchaseRequest);
    }
}
