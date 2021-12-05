package com.kat.kafkapersonconsumer.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfig {

    public static final String PERSON_TEST_TOPIC = "person_test_topic";

    @Bean
    public NewTopic personTestTopic() {
        return new NewTopic(PERSON_TEST_TOPIC, 1, (short)1);
    }
}
