package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.UserSubscriptionAction;
import com.kat.kafkaorderproducer.controller.request.UserSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

	private final UserSubscriptionAction userSubscriptionAction;

	public void createUser(UserSubscriptionRequest userSubscriptionRequest) {
		userSubscriptionAction.publishToKafka(userSubscriptionRequest);
	}
}
