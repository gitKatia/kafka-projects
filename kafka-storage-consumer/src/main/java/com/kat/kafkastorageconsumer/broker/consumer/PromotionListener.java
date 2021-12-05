package com.kat.kafkastorageconsumer.broker.consumer;

import com.kat.ordersmodel.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${kafka-storage-consumer.promotions-topic}")
@Slf4j
public class PromotionListener {

	@KafkaHandler
	public void listenPromotion(PromotionMessage message) {
		log.info("Processing promotion : {}", message);
	}

}
