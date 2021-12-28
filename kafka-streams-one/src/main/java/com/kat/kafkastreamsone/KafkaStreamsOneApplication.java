package com.kat.kafkastreamsone;

import com.kat.kafkastreamsone.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsOneApplication.class, args);
    }
}
