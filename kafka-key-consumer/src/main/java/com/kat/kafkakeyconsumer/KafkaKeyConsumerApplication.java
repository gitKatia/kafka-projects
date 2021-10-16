package com.kat.kafkakeyconsumer;

import com.kat.kafkakeyconsumer.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaKeyConsumerApplication{
    public static void main(String[] args) {
        SpringApplication.run(KafkaKeyConsumerApplication.class, args);
    }
}
