package com.kat.kafkafeedbackonestreams;

import com.kat.kafkafeedbackonestreams.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFeedbackOneStreamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFeedbackOneStreamsApplication.class, args);
    }
}
