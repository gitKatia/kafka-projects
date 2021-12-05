package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;
    private final TopicsProperties topicProperties;

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage orderMessage) {
        long surpriseBonus = StringUtils.startsWithIgnoreCase(orderMessage.getOrderLocation(), "A") ? 25 : 15;

        List<Header> headers = new ArrayList<>();
        RecordHeader surpriseBonusHeader = new RecordHeader("surpriseBonus", Long.toString(surpriseBonus).getBytes());
        headers.add(surpriseBonusHeader);

        return new ProducerRecord<>(topicProperties.getOrdersTopic(), null, orderMessage.getOrderNumber(), orderMessage,
                headers);
    }

    public void publish(OrderMessage orderMessage) {
        ProducerRecord producerRecord = buildProducerRecord(orderMessage);

        kafkaTemplate.send(producerRecord)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Order {}, item {} failed to publish, because {}", orderMessage.getOrderNumber(),
                                orderMessage.getItemName(), ex.getMessage());
                        // Send to Elastic search
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> result) {
                        log.info("Order {}, item {} published successfully", orderMessage.getOrderNumber(),
                                orderMessage.getItemName());
                    }
                });
    }
}
