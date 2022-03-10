package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.InventoryProducer;
import com.kat.kafkaorderproducer.controller.request.InventoryRequest;
import com.kat.ordersmodel.InventoryMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryAction {
    private final InventoryProducer inventoryProducer;

    public void publishToKafka(InventoryRequest inventoryRequest, String type) {
        InventoryMessage inventoryMessage = InventoryMessage.builder()
                .item(inventoryRequest.getItem())
                .location(inventoryRequest.getLocation())
                .quantity(inventoryRequest.getQuantity())
                .type(type)
                .transactionTime(inventoryRequest.getTransactionTime())
                .build();
        inventoryProducer.publish(inventoryMessage);
    }
}
