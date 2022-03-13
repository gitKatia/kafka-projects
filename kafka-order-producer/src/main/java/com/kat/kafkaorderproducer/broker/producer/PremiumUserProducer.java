package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.PremiumUserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PremiumUserProducer {

	private final KafkaTemplate<String, PremiumUserMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(PremiumUserMessage premiumUserMessage) {
		// published with username as the Key
		kafkaTemplate.send(topicsProperties.getPremiumUserTopic(), premiumUserMessage.getUsername(), premiumUserMessage);
	}
}
