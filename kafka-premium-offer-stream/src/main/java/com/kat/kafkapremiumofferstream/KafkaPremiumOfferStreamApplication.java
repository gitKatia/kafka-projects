package com.kat.kafkapremiumofferstream;

import com.kat.kafkapremiumofferstream.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaPremiumOfferStreamApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaPremiumOfferStreamApplication.class, args);
    }
}
