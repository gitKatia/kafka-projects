package com.kat.kafkafeedbackthreestreams;

import com.kat.kafkafeedbackthreestreams.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFeedbackThreeStreamsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFeedbackThreeStreamsApplication.class, args);
    }
}
