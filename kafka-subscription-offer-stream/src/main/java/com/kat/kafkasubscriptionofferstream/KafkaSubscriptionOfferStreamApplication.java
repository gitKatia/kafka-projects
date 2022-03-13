package com.kat.kafkasubscriptionofferstream;

import com.kat.kafkasubscriptionofferstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaSubscriptionOfferStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaSubscriptionOfferStreamApplication.class, args);
    }
}
