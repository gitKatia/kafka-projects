package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.FeedbackProducer;
import com.kat.kafkaorderproducer.controller.request.FeedbackRequest;
import com.kat.ordersmodel.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FeedbackAction {
    private final FeedbackProducer feedbackProducer;

    public void publishToKafka(FeedbackRequest feedbackRequest) {
        FeedbackMessage feedbackMessage = new FeedbackMessage();
        feedbackMessage.setFeedback(feedbackRequest.getFeedback());
        feedbackMessage.setLocation(feedbackRequest.getLocation());
        feedbackMessage.setRating(feedbackRequest.getRating());
        feedbackMessage.setFeedbackDateTime(LocalDateTime.now());
        feedbackProducer.publish(feedbackMessage);
    }
}
