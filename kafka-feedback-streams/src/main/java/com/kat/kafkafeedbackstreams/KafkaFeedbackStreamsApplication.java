package com.kat.kafkafeedbackstreams;

import com.kat.kafkafeedbackstreams.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFeedbackStreamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFeedbackStreamsApplication.class, args);
    }
}
