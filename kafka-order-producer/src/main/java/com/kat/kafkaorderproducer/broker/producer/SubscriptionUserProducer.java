package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.UserSubscriptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionUserProducer {

	private final KafkaTemplate<String, UserSubscriptionMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(UserSubscriptionMessage userSubscriptionMessage) {
		kafkaTemplate.send(topicsProperties.getUserSubscriptionTopic(), userSubscriptionMessage.getUsername(), userSubscriptionMessage);
	}
}
