package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionProducer {

    private final KafkaTemplate<String, PromotionMessage> kafkaTemplate;
    private final TopicsProperties topicsProperties;

    public void publish(PromotionMessage promotionMessage) {
        try {
            log.info("Sending promotion {} to Kafka", promotionMessage);
            // Blocking the sender thread to await the result
            SendResult<String, PromotionMessage> sendResult = kafkaTemplate.send(topicsProperties.getPromotionsTopic(),
                    promotionMessage).get();
            log.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e ) {
           log.error("Error while sending message to Kafka topic {}", topicsProperties.getPromotionsTopic());
        }
    }
}
