package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.FlashSaleVoteProducer;
import com.kat.kafkaorderproducer.controller.request.FlashSaleVoteRequest;
import com.kat.ordersmodel.FlashSaleVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlashSaleVoteAction {
    private final FlashSaleVoteProducer flashSaleVoteProducer;

    public void publishToKafka(FlashSaleVoteRequest flashSaleVoteRequest) {
        FlashSaleVoteMessage flashSaleVoteMessage = FlashSaleVoteMessage.builder()
                .customerId(flashSaleVoteRequest.getCustomerId())
                .itemName(flashSaleVoteRequest.getItemName())
                .build();
        flashSaleVoteProducer.publish(flashSaleVoteMessage);
    }
}
