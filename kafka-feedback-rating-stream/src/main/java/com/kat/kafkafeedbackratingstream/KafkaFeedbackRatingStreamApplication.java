package com.kat.kafkafeedbackratingstream;

import com.kat.kafkafeedbackratingstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFeedbackRatingStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFeedbackRatingStreamApplication.class, args);
    }
}
