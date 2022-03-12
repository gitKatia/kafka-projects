package com.kat.kafkaorderpaymentleftjoinstream;

import com.kat.kafkaorderpaymentleftjoinstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaOrderPaymentLeftJoinStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaOrderPaymentLeftJoinStreamApplication.class, args);
    }
}
