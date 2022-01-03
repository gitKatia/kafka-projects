package com.kat.kafkastreamssix;

import com.kat.kafkastreamssix.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsSixApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsSixApplication.class, args);
    }
}
