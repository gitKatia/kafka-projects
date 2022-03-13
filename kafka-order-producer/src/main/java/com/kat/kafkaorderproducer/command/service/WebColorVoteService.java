package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.WebColorVoteAction;
import com.kat.kafkaorderproducer.controller.request.WebColorVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebColorVoteService {

	private final WebColorVoteAction webColorVoteAction;

	public void createColorVote(WebColorVoteRequest webColorVoteRequest) {
		webColorVoteAction.publishToKafka(webColorVoteRequest);
	}
}
