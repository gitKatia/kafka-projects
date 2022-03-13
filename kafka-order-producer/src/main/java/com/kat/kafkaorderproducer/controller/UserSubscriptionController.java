package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.UserSubscriptionService;
import com.kat.kafkaorderproducer.controller.request.UserSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createUser(@RequestBody UserSubscriptionRequest userSubscriptionRequest) {
        userSubscriptionService.createUser(userSubscriptionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User subscription created: " + userSubscriptionRequest);
    }
}
