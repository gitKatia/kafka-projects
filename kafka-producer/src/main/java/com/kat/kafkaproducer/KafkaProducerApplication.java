package com.kat.kafkaproducer;

import com.kat.kafkaproducer.config.TopicsProperties;
import com.kat.kafkaproducer.producer.BasicKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {

    @Autowired
    private BasicKafkaProducer basicKafkaProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        basicKafkaProducer.sendRandomInt();
    }
}
