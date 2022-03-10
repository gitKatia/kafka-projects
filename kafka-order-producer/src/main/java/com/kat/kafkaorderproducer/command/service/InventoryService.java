package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.InventoryAction;
import com.kat.kafkaorderproducer.controller.request.InventoryRequest;
import com.kat.ordersmodel.InventoryOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryAction inventoryAction;

    public void add(InventoryRequest inventoryRequest) {
        inventoryAction.publishToKafka(inventoryRequest, InventoryOperation.ADD.getOperation());
    }

    public void subtract(InventoryRequest inventoryRequest) {
        inventoryAction.publishToKafka(inventoryRequest, InventoryOperation.REMOVE.getOperation());
    }
}
