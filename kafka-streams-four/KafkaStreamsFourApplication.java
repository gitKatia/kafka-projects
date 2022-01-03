package com.kat.kafkastreamsthree;

import com.kat.kafkastreamsthree.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaStreamsThreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsFourApplication.class, args);
    }
}
