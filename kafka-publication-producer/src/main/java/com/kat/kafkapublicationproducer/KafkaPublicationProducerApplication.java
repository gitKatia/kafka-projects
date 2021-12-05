package com.kat.kafkapublicationproducer;

import com.kat.kafkapublicationproducer.conf.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class KafkaPublicationProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaPublicationProducerApplication.class, args);
    }
}
