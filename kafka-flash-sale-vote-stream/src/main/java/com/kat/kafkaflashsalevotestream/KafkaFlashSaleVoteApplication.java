package com.kat.kafkaflashsalevotestream;

import com.kat.kafkaflashsalevotestream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaFlashSaleVoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaFlashSaleVoteApplication.class, args);
    }
}
