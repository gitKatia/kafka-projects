package com.kat.kafkaflashsalevotesstream;

import com.kat.kafkaflashsalevotesstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFlashSaleVotesApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFlashSaleVotesApplication.class, args);
    }
}
