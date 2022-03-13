package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.PremiumUserAction;
import com.kat.kafkaorderproducer.controller.request.PremiumUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PremiumUserService {

	private final PremiumUserAction premiumPurchaseAction;

	public void createUser(PremiumUserRequest premiumPurchaseRequest) {
		premiumPurchaseAction.publishToKafka(premiumPurchaseRequest);
	}
}
