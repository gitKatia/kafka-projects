package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.DiscountProducer;
import com.kat.kafkaorderproducer.controller.request.DiscountRequest;
import com.kat.ordersmodel.DiscountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountAction {

    private final DiscountProducer discountProducer;

    public void publishToKafka(DiscountRequest discountRequest) {
        DiscountMessage discountMessage = new DiscountMessage(discountRequest.getDiscountCode(),
                discountRequest.getDiscountPercentage());
        discountProducer.publish(discountMessage);
    }
}
