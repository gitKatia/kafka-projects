package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.SubscriptionUserProducer;
import com.kat.kafkaorderproducer.controller.request.UserSubscriptionRequest;
import com.kat.ordersmodel.UserSubscriptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserSubscriptionAction {

	private final SubscriptionUserProducer subscriptionUserProducer;

	public void publishToKafka(UserSubscriptionRequest userSubscriptionRequest) {
		UserSubscriptionMessage userSubscriptionMessage = UserSubscriptionMessage.builder()
				.duration(userSubscriptionRequest.getDuration())
				.username(userSubscriptionRequest.getUsername())
				.build();
		subscriptionUserProducer.publish(userSubscriptionMessage);
	}

}
