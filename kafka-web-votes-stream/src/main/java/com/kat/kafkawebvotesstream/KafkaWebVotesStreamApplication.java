package com.kat.kafkawebvotesstream;

import com.kat.kafkawebvotesstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaWebVotesStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaWebVotesStreamApplication.class, args);
    }
}
