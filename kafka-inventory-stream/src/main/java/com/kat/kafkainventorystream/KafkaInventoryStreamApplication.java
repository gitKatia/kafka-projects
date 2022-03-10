package com.kat.kafkainventorystream;

import com.kat.kafkainventorystream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaInventoryStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaInventoryStreamApplication.class, args);
    }
}
