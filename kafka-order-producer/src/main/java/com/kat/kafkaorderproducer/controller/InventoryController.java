package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.InventoryService;
import com.kat.kafkaorderproducer.controller.request.InventoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> add(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.add(inventoryRequest);

        return ResponseEntity.ok().body("Added to inventory : " + inventoryRequest.toString());
    }

    @PostMapping(value = "/subtract", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> subtract(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.subtract(inventoryRequest);

        return ResponseEntity.ok().body("Removed from inventory : " + inventoryRequest.toString());
    }
}
