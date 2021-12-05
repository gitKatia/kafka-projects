package com.kat.kafkaorderproducer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final TopicsProperties topicsProperties;

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name(topicsProperties.getOrdersTopic())
                .partitions(topicsProperties.getOrdersTopicPartitions())
                .replicas(topicsProperties.getOrdersTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic ordersReplyToTopic() {
        return TopicBuilder.name(topicsProperties.getOrdersReplyToTopic())
                .partitions(topicsProperties.getOrdersReplyToTopicPartitions())
                .replicas(topicsProperties.getOrdersReplyToTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic promotionsTopic() {
        return TopicBuilder.name(topicsProperties.getPromotionsTopic())
                .partitions(topicsProperties.getPromotionsTopicPartitions())
                .replicas(topicsProperties.getPromotionsTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic discountsTopic() {
        return TopicBuilder.name(topicsProperties.getDiscountsTopic())
                .partitions(topicsProperties.getDiscountsTopicPartitions())
                .replicas(topicsProperties.getDiscountsTopicReplicas())
                .build();
    }
}
