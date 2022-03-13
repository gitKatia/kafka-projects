package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.WebLayoutVoteProducer;
import com.kat.kafkaorderproducer.controller.request.WebLayoutVoteRequest;
import com.kat.ordersmodel.WebLayoutVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebLayoutVoteAction {

	private final WebLayoutVoteProducer webLayoutVoteProducer;

	public void publishToKafka(WebLayoutVoteRequest webLayoutVoteRequest) {
		WebLayoutVoteMessage webLayoutVoteMessage = WebLayoutVoteMessage.builder()
				.username(webLayoutVoteRequest.getUsername())
				.layout(webLayoutVoteRequest.getLayout())
				.voteDateTime(webLayoutVoteRequest.getVoteDateTime())
				.build();
		webLayoutVoteProducer.publish(webLayoutVoteMessage);
	}
}
