package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.FeedbackAction;
import com.kat.kafkaorderproducer.controller.request.FeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackAction feedbackAction;

    public void createFeedback(FeedbackRequest feedbackRequest) {
        feedbackAction.publishToKafka(feedbackRequest);
    }
}
