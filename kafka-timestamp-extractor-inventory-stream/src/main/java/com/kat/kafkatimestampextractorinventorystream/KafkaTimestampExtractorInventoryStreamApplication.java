package com.kat.kafkatimestampextractorinventorystream;

import com.kat.kafkatimestampextractorinventorystream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaTimestampExtractorInventoryStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaTimestampExtractorInventoryStreamApplication.class, args);
    }
}
