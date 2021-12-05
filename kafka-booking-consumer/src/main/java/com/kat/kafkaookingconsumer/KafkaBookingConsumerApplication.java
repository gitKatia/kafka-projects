package com.kat.kafkaookingconsumer;

import com.kat.kafkaookingconsumer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaBookingConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaBookingConsumerApplication.class, args);
    }
}
