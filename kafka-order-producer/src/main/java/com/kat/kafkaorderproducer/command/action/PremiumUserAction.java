package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.PremiumUserProducer;
import com.kat.kafkaorderproducer.controller.request.PremiumUserRequest;
import com.kat.ordersmodel.PremiumUserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PremiumUserAction {

	private final PremiumUserProducer premiumUserProducer;

	public void publishToKafka(PremiumUserRequest premiumUserRequest) {
		PremiumUserMessage premiumUserMessage = PremiumUserMessage.builder()
				.username(premiumUserRequest.getUsername())
				.level(premiumUserRequest.getLevel())
				.build();

		premiumUserProducer.publish(premiumUserMessage);
	}

}
