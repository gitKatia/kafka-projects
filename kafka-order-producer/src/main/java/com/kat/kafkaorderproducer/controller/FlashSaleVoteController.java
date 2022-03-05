package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.FlashSaleVoteService;
import com.kat.kafkaorderproducer.controller.request.FlashSaleVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/flashsalevotes")
@RequiredArgsConstructor
public class FlashSaleVoteController {
    private final FlashSaleVoteService flashSaleVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody FlashSaleVoteRequest flashSaleVoteRequest) {
        flashSaleVoteService.createFlashSaleVote(flashSaleVoteRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(format("Flash sale vote created for customer %s and item name %s",
                        flashSaleVoteRequest.getCustomerId(), flashSaleVoteRequest.getItemName()));
    }
}
