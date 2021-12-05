package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.PromotionAction;
import com.kat.kafkaorderproducer.controller.request.PromotionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionAction promotionAction;

    public void createPromotion(PromotionRequest promotionRequest) {
        promotionAction.publishToKafka(promotionRequest);
    }
}
