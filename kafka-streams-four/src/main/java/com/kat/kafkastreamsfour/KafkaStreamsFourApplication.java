package com.kat.kafkastreamsfour;

import com.kat.kafkastreamsfour.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsFourApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsFourApplication.class, args);
    }
}
