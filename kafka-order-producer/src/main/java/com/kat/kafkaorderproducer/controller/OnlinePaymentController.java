package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.OnlinePaymentService;
import com.kat.kafkaorderproducer.controller.request.OnlinePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OnlinePaymentController {
    private final OnlinePaymentService onlinePaymentService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createOnlinePayment(@RequestBody OnlinePaymentRequest onlinePaymentRequest) {
        onlinePaymentService.publishPaymentToKafka(onlinePaymentRequest);
        return ResponseEntity.ok().body("Saved online payment " + onlinePaymentRequest.getOnlineOrderNumber());
    }
}
