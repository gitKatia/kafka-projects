package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.PremiumPurchaseProducer;
import com.kat.kafkaorderproducer.controller.request.PremiumPurchaseRequest;
import com.kat.ordersmodel.PremiumPurchaseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PremiumPurchaseAction {

	private final PremiumPurchaseProducer premiumPurchaseProducer;

	public void publishToKafka(PremiumPurchaseRequest premiumPurchaseRequest) {
		PremiumPurchaseMessage premiumPurchaseMessage = PremiumPurchaseMessage.builder()
				.username(premiumPurchaseRequest.getUsername())
				.item(premiumPurchaseRequest.getItem())
				.purchaseNumber(premiumPurchaseRequest.getPurchaseNumber())
				.build();

		premiumPurchaseProducer.publish(premiumPurchaseMessage);
	}
}
