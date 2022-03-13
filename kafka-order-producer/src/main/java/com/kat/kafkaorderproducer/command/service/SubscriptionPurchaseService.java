package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.SubscriptionPurchaseAction;
import com.kat.kafkaorderproducer.controller.request.SubscriptionPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionPurchaseService {

	private final SubscriptionPurchaseAction subscriptionPurchaseAction;

	public void createPurchase(SubscriptionPurchaseRequest subscriptionPurchaseRequest) {
		subscriptionPurchaseAction.publishToKafka(subscriptionPurchaseRequest);
	}
}
