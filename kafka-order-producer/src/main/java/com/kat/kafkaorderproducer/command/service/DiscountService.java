package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.DiscountAction;
import com.kat.kafkaorderproducer.controller.request.DiscountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountAction discountAction;

    public void createDiscount(DiscountRequest discountRequest) {
        discountAction.publishToKafka(discountRequest);
    }
}
