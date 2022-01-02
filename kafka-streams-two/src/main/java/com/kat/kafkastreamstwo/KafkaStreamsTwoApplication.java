package com.kat.kafkastreamstwo;

import com.kat.kafkastreamstwo.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsTwoApplication.class, args);
    }
}
