package com.kat.kafkastreamsfive;

import com.kat.kafkastreamsfive.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsFiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsFiveApplication.class, args);
    }
}
