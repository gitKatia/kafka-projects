package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.WebColorVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WebColorVoteProducer {

	private final KafkaTemplate<String, WebColorVoteMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(WebColorVoteMessage message) {
		// Key is username
		kafkaTemplate.send(topicsProperties.getWebColorVoteTopic(), message.getUsername(), message);
	}

}
