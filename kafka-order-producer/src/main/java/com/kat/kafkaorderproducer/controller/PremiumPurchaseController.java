package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.PremiumPurchaseService;
import com.kat.kafkaorderproducer.controller.request.PremiumPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/premium-purchases")
public class PremiumPurchaseController {
    private final PremiumPurchaseService premiumPurchaseService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createUser(@RequestBody PremiumPurchaseRequest premiumPurchaseRequest) {
        premiumPurchaseService.createPurchase(premiumPurchaseRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created purchase: " + premiumPurchaseRequest);
    }
}
