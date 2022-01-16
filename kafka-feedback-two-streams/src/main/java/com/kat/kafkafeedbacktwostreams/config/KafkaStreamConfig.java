package com.kat.kafkafeedbacktwostreams.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamConfig {

    private final TopicsProperties topicsProperties;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {

        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-feedback-two-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "3000");
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public NewTopic feedbackTwoGoodTopic() {
        return TopicBuilder.name(topicsProperties.getFeedbackTwoGoodTopic())
                .partitions(topicsProperties.getFeedbackTwoGoodTopicPartitions())
                .replicas(topicsProperties.getFeedbackTwoGoodTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic feedbackTwoGoodCountTopic() {
        return TopicBuilder.name(topicsProperties.getFeedbackTwoGoodCountTopic())
                .partitions(topicsProperties.getFeedbackTwoGoodCountTopicPartitions())
                .replicas(topicsProperties.getFeedbackTwoGoodCountTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic feedbackTwBadTopic() {
        return TopicBuilder.name(topicsProperties.getFeedbackTwoBadTopic())
                .partitions(topicsProperties.getFeedbackTwoBadTopicPartitions())
                .replicas(topicsProperties.getFeedbackTwoBadTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic feedbackTwoBadCountTopic() {
        return TopicBuilder.name(topicsProperties.getFeedbackTwoBadCountTopic())
                .partitions(topicsProperties.getFeedbackTwoBadCountTopicPartitions())
                .replicas(topicsProperties.getFeedbackTwoBadCountTopicReplicas())
                .build();
    }
}
