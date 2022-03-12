package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.OnlineOrderAction;
import com.kat.kafkaorderproducer.controller.request.OnlineOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnlineOrderService {
    private final OnlineOrderAction onlineOrderAction;

    public void publishOrderToKafka(OnlineOrderRequest onlineOrderRequest) {
        onlineOrderAction.publishToKafka(onlineOrderRequest);
    }
}
