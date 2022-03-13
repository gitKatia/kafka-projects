package com.kat.kafkaorderproducer.command.service;

import com.kat.kafkaorderproducer.command.action.WebLayoutVoteAction;
import com.kat.kafkaorderproducer.controller.request.WebLayoutVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebLayoutVoteService {

	private final WebLayoutVoteAction webLayoutVoteAction;

	public void createLayoutVote(WebLayoutVoteRequest webLayoutVoteRequest) {
		webLayoutVoteAction.publishToKafka(webLayoutVoteRequest);
	}
}
