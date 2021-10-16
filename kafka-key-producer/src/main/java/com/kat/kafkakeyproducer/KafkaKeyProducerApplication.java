package com.kat.kafkakeyproducer;

import com.kat.kafkakeyproducer.config.TopicsProperties;
import com.kat.kafkakeyproducer.producer.KafkaKeyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.stream.IntStream;

import static java.lang.String.format;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
public class KafkaKeyProducerApplication implements CommandLineRunner {

    @Autowired
    private KafkaKeyProducer kafkaKeyProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaKeyProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        IntStream.range(0, 30).forEach(k -> {
            String key = k + "-" + k%2;
            String value = format("\"Value %s has %s\"", k, key);
            kafkaKeyProducer.send(key, value);
        });
    }
}
