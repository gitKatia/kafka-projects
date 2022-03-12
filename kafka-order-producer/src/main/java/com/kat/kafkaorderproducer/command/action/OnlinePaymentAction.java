package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.OnlinePaymentProducer;
import com.kat.kafkaorderproducer.controller.request.OnlinePaymentRequest;
import com.kat.ordersmodel.OnlinePaymentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OnlinePaymentAction {
    private final OnlinePaymentProducer onlinePaymentProducer;

    public void publishPaymentToKafka(OnlinePaymentRequest onlinePaymentRequest) {
        OnlinePaymentMessage onlinePaymentMessage = OnlinePaymentMessage.builder()
                .onlineOrderNumber(onlinePaymentRequest.getOnlineOrderNumber())
                .paymentDateTime(Optional.ofNullable(onlinePaymentRequest.getPaymentDateTime()).orElseGet(() -> LocalDateTime.now()))
                .paymentMethod(onlinePaymentRequest.getPaymentMethod())
                .paymentNumber(UUID.randomUUID().toString())
                .build();
        onlinePaymentProducer.publish(onlinePaymentMessage);
    }
}
