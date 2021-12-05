package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.DiscountService;
import com.kat.kafkaorderproducer.controller.request.DiscountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discounts")
@Slf4j
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody DiscountRequest discountRequest) {
        discountService.createDiscount(discountRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(discountRequest.getDiscountCode() + " with " + discountRequest.getDiscountPercentage()
                        + "% discount");
    }
}
