package com.kat.kafkapatternconsumer.broker.consumer;

import com.kat.ordersmodel.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderListener {

    @KafkaListener(topics = "${kafka-pattern-consumer.orders-topic}")
    public void listen(OrderMessage orderMessage) {
        double totalItemAmount = orderMessage.getPrice() * orderMessage.getQuantity();
        log.info("Processing order {}, item {}. Total amount for this item is {}",
                orderMessage.getOrderNumber(), orderMessage.getItemName(), totalItemAmount);
    }
}
