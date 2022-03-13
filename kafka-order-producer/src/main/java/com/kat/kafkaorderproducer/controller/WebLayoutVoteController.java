package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.WebLayoutVoteService;
import com.kat.kafkaorderproducer.controller.request.WebLayoutVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web-layout-votes")
@RequiredArgsConstructor
public class WebLayoutVoteController {
    private final WebLayoutVoteService webLayoutVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createLayoutVote(@RequestBody WebLayoutVoteRequest webLayoutVoteRequest) {
        webLayoutVoteService.createLayoutVote(webLayoutVoteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Layout vote created: " + webLayoutVoteRequest);
    }
}
