package com.kat.kafkapersonconsumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkapersonconsumer.config.TopicsProperties;
import com.kat.kafkapersonconsumer.model.Person;
import lombok.extern.slf4j.Slf4j;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.concurrent.ListenableFuture;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.kat.kafkapersonconsumer.KafkaPersonConsumerApplicationConstants.PERSON_AGE_GROUP_ID;
import static com.kat.kafkapersonconsumer.KafkaPersonConsumerApplicationConstants.PERSON_GROUP_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@Slf4j
public class KafkaPersonConsumerIntegrationTest {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private TopicsProperties topicsProperties;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PERSON_TEST_TOPIC = "person_test_topic";

    @Container
    private static KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.kafka.properties.bootstrap.servers", () -> KAFKA_CONTAINER.getBootstrapServers());
        registry.add("kafka-person-consumer.person-topic", () -> PERSON_TEST_TOPIC);
        registry.add("spring.kafka.consumer.properties.auto.offset.reset", () -> "earliest");
    }

    @Test
    public void testKafkaIsRunning() {
        assertTrue(KAFKA_CONTAINER.isRunning());
    }

    @DisplayName("test a person is processed by a single consumer group because of filtering for age = age filter")
    @Test
    public void testRecordIsNotProcessedGivenEdgeCase() throws IOException, InterruptedException, ExecutionException {

        // Given
        int age = topicsProperties.getAgeFilter();
        Person person = Person.builder()
                .personId(UUID.randomUUID().toString())
                .age(age)
                .build();
        LogCaptor logCaptor = LogCaptor.forClass(KafkaPersonConsumer.class);

        // When
        String personAsString = objectMapper.writeValueAsString(person);
        ListenableFuture<SendResult<String,String>> a = kafkaTemplate.send(PERSON_TEST_TOPIC, person.getPersonId(), personAsString);
        while(!a.isDone()) {
            Thread.sleep(1000);
        }

        // Then
        List<String> infoLogs = logCaptor.getInfoLogs();
        assertThat(infoLogs.size()).isEqualTo(1);
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_GROUP_ID)).findFirst().isPresent()).isTrue();
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_AGE_GROUP_ID)).findFirst().isPresent()).isFalse();
    }

    @DisplayName("test a person is processed by a single consumer group because of filtering")
    @Test
    public void testRecordIsNotProcessedForAllAges() throws IOException, InterruptedException {

        // Given
        int age = topicsProperties.getAgeFilter() - 1;
        Person person = Person.builder()
                .personId(UUID.randomUUID().toString())
                .age(age)
                .build();
        LogCaptor logCaptor = LogCaptor.forClass(KafkaPersonConsumer.class);

        // When
        String personAsString = objectMapper.writeValueAsString(person);
        ListenableFuture<SendResult<String,String>> a = kafkaTemplate.send(PERSON_TEST_TOPIC, person.getPersonId(), personAsString);
        while(!a.isDone()) {
            Thread.sleep(1000);
        }

        // Then
        List<String> infoLogs = logCaptor.getInfoLogs();
        assertThat(infoLogs.size()).isEqualTo(1);
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_GROUP_ID)).findFirst().isPresent()).isTrue();
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_AGE_GROUP_ID)).findFirst().isPresent()).isFalse();
    }

    @DisplayName("test a person is processed by both consumer groups")
    @Test
    public void testRecordIsProcessedForAllAges() throws IOException, InterruptedException {

        // Given
        Person person = Person.builder()
                .personId(UUID.randomUUID().toString())
                .age(24)
                .build();
        LogCaptor logCaptor = LogCaptor.forClass(KafkaPersonConsumer.class);

        // When
        String personAsString = objectMapper.writeValueAsString(person);
        ListenableFuture<SendResult<String,String>> a = kafkaTemplate.send(PERSON_TEST_TOPIC, person.getPersonId(), personAsString);
        while(!a.isDone()) {
            Thread.sleep(1000);
        }

        // Then
        List<String> infoLogs = logCaptor.getInfoLogs();
        assertThat(infoLogs.size()).isEqualTo(2);
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_GROUP_ID)).findFirst().isPresent()).isTrue();
        assertThat(infoLogs.stream().filter(infoLog -> infoLog.contains(PERSON_AGE_GROUP_ID)).findFirst().isPresent()).isTrue();
    }
}
