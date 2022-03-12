package com.kat.kafkaorderpaymentinnerjoinstream;

import com.kat.kafkaorderpaymentinnerjoinstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaOrderPaymentInnerJoinStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaOrderPaymentInnerJoinStreamApplication.class, args);
    }
}
