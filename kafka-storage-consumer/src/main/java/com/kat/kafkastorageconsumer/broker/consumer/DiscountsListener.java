package com.kat.kafkastorageconsumer.broker.consumer;

import com.kat.ordersmodel.DiscountMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${kafka-storage-consumer.discounts-topic}")
@Slf4j
public class DiscountsListener {

    @KafkaHandler
    public void listenDiscount(DiscountMessage message) {
        log.info("Processing discount : {}", message);
    }
}
