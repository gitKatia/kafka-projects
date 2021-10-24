package com.kat.kafkapersonconsumer.consumer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public NewTopic personTestTopic() {
        // Make it configurable
        return new NewTopic("person_test_topic", 1, (short)1);
    }
}
