package com.kat.kafkarebalancedproducer;

import com.kat.kafkarebalancedproducer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class KafkaRebalancedProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaRebalancedProducerApplication.class, args);
    }
}
