package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.PromotionProducer;
import com.kat.kafkaorderproducer.controller.request.PromotionRequest;
import com.kat.ordersmodel.PromotionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionAction {

    private final PromotionProducer promotionProducer;

    public void publishToKafka(PromotionRequest promotionRequest) {
        PromotionMessage promotionMessage = new PromotionMessage(promotionRequest.getPromotionCode());
        promotionProducer.publish(promotionMessage);
    }
}
