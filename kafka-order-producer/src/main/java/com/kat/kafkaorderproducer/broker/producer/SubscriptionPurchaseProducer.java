package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.SubscriptionPurchaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionPurchaseProducer {

	private final KafkaTemplate<String, SubscriptionPurchaseMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(SubscriptionPurchaseMessage subscriptionPurchaseMessage) {
		kafkaTemplate.send(topicsProperties.getSubscriptionPurchaseTopic(), subscriptionPurchaseMessage.getUsername(), subscriptionPurchaseMessage);
	}
}
