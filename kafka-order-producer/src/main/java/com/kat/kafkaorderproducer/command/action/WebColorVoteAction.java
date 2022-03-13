package com.kat.kafkaorderproducer.command.action;

import com.kat.kafkaorderproducer.broker.producer.WebColorVoteProducer;
import com.kat.kafkaorderproducer.controller.request.WebColorVoteRequest;
import com.kat.ordersmodel.WebColorVoteMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebColorVoteAction {

	private final WebColorVoteProducer webLayoutVoteProducer;

	public void publishToKafka(WebColorVoteRequest webLayoutVoteRequest) {
		WebColorVoteMessage webLayoutVoteMessage = WebColorVoteMessage.builder()
				.username(webLayoutVoteRequest.getUsername())
				.color(webLayoutVoteRequest.getColor())
				.voteDateTime(webLayoutVoteRequest.getVoteDateTime())
				.build();
		webLayoutVoteProducer.publish(webLayoutVoteMessage);
	}

}
