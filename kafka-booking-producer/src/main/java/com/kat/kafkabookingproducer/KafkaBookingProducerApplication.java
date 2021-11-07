package com.kat.kafkabookingproducer;

import com.kat.kafkabookingproducer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class KafkaBookingProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaBookingProducerApplication.class, args);
    }
}
