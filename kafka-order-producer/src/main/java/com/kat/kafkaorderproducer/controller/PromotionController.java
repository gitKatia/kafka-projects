package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.PromotionService;
import com.kat.kafkaorderproducer.controller.request.PromotionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody PromotionRequest promotionRequest) {
        promotionService.createPromotion(promotionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionRequest.getPromotionCode());
    }
}
