package com.kat.kafkainventoryreducestream;

import com.kat.kafkainventoryreducestream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaInventoryReduceStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaInventoryReduceStreamApplication.class, args);
    }
}
