package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.PremiumPurchaseAction;
import com.kat.kafkaorderproducer.controller.request.PremiumPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PremiumPurchaseService {

	private final PremiumPurchaseAction premiumPurchaseAction;

	public void createPurchase(PremiumPurchaseRequest premiumPurchaseRequest) {
		premiumPurchaseAction.publishToKafka(premiumPurchaseRequest);
	}
}
