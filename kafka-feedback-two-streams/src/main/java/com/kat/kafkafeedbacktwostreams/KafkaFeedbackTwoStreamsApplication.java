package com.kat.kafkafeedbacktwostreams;

import com.kat.kafkafeedbacktwostreams.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFeedbackTwoStreamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFeedbackTwoStreamsApplication.class, args);
    }
}
