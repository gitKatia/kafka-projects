package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.OnlinePaymentAction;
import com.kat.kafkaorderproducer.controller.request.OnlinePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlinePaymentService {
    private final OnlinePaymentAction onlinePaymentAction;

    public void publishPaymentToKafka(OnlinePaymentRequest onlinePaymentRequest) {
        onlinePaymentAction.publishPaymentToKafka(onlinePaymentRequest);
    }
}
