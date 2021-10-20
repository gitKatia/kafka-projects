package com.kat.kafkaproductproducer;

import com.kat.kafkaproductproducer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class KafkaProductProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProductProducerApplication.class, args);
    }
}
