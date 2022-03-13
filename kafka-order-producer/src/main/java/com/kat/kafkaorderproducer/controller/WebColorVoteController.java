package com.kat.kafkaorderproducer.controller;

import com.kat.kafkaorderproducer.command.service.WebColorVoteService;
import com.kat.kafkaorderproducer.controller.request.WebColorVoteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web-color-votes")
@RequiredArgsConstructor
public class WebColorVoteController {
    private final WebColorVoteService webColorVoteService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> createColorVote(@RequestBody WebColorVoteRequest webColorVoteRequest) {
        webColorVoteService.createColorVote(webColorVoteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Color vote created: " + webColorVoteRequest);
    }
}
