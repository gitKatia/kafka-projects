package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.PremiumPurchaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PremiumPurchaseProducer {

	private final KafkaTemplate<String, PremiumPurchaseMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(PremiumPurchaseMessage premiumPurchaseMessage) {
		// Pubblished with purchase number as the Key
		kafkaTemplate.send(topicsProperties.getPremiumPurchaseTopic(), premiumPurchaseMessage.getPurchaseNumber(), premiumPurchaseMessage);
	}
}
