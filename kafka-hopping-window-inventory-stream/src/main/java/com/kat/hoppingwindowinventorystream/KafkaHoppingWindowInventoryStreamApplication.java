package com.kat.hoppingwindowinventorystream;

import com.kat.hoppingwindowinventorystream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaHoppingWindowInventoryStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaHoppingWindowInventoryStreamApplication.class, args);
    }
}
