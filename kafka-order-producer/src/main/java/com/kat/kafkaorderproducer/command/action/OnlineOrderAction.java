package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.OnlineOrderProducer;
import com.kat.kafkaorderproducer.controller.request.OnlineOrderRequest;
import com.kat.ordersmodel.OnlineOrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnlineOrderAction {
    private final OnlineOrderProducer onlineOrderProducer;

    public void publishToKafka(OnlineOrderRequest onlineOrderRequest) {
        OnlineOrderMessage onlineOrderMessage = OnlineOrderMessage.builder()
                .onlineOrderNumber(onlineOrderRequest.getOnlineOrderNumber())
                .orderDateTime(onlineOrderRequest.getOrderDateTime())
                .totalAmount(onlineOrderRequest.getTotalAmount())
                .username(onlineOrderRequest.getUsername().toLowerCase())
                .build();

        onlineOrderProducer.publish(onlineOrderMessage);
    }
}
