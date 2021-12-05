package com.kat.kafkaorderproducer.broker.consumer;

import com.kat.ordersmodel.OrderReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderReplyListener {

    @KafkaListener(topics = "${kafka-order-producer.orders-reply-to-topic}")
    public void listen(OrderReplyMessage orderReplyMessage) {
        log.info("Received order reply message {}", orderReplyMessage);
    }
}
