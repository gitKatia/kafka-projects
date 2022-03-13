package com.kat.kafkaorderproducer.broker.producer;

import com.kat.kafkaorderproducer.config.TopicsProperties;
import com.kat.ordersmodel.WebLayoutVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WebLayoutVoteProducer {

	private final KafkaTemplate<String, WebLayoutVoteMessage> kafkaTemplate;
	private final TopicsProperties topicsProperties;

	public void publish(WebLayoutVoteMessage webLayoutVoteMessage) {
		// Key is username
		kafkaTemplate.send(topicsProperties.getWebLayoutVoteTopic(), webLayoutVoteMessage.getUsername(), webLayoutVoteMessage);
	}

}
