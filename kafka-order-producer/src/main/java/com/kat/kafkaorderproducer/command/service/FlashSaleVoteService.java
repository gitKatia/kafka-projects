package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.FlashSaleVoteAction;
import com.kat.kafkaorderproducer.controller.request.FlashSaleVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlashSaleVoteService {
    private final FlashSaleVoteAction flashSaleVoteAction;

    public void createFlashSaleVote(FlashSaleVoteRequest flashSaleVoteRequest) {
        flashSaleVoteAction.publishToKafka(flashSaleVoteRequest);
    }
}
