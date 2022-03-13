package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.SubscriptionPurchaseProducer;
import com.kat.kafkaorderproducer.controller.request.SubscriptionPurchaseRequest;
import com.kat.ordersmodel.SubscriptionPurchaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionPurchaseAction {

	private final SubscriptionPurchaseProducer subscriptionPurchaseProducer;

	public void publishToKafka(SubscriptionPurchaseRequest subscriptionPurchaseRequest) {
		SubscriptionPurchaseMessage subscriptionPurchaseMessage = SubscriptionPurchaseMessage.builder()
				.subscriptionNumber(subscriptionPurchaseRequest.getSubscriptionNumber())
				.username(subscriptionPurchaseRequest.getUsername())
				.build();
		subscriptionPurchaseProducer.publish(subscriptionPurchaseMessage);
	}
}
