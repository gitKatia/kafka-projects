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

    @Bean
    public NewTopic feedbackTopic() {
        return TopicBuilder.name(topicsProperties.getFeedbackTopic())
                .partitions(topicsProperties.getFeedbackTopicPartitions())
                .replicas(topicsProperties.getFeedbackTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic flashSaleVotesTopic() {
        return TopicBuilder.name(topicsProperties.getFlashSaleVotesTopic())
                .partitions(topicsProperties.getFlashSaleVotesTopicPartitions())
                .replicas(topicsProperties.getFlashSaleVotesTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic inventoryTopic() {
        return TopicBuilder.name(topicsProperties.getInventoryTopic())
                .partitions(topicsProperties.getInventoryTopicPartitions())
                .replicas(topicsProperties.getInventoryTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic onlineOrderTopic() {
        return TopicBuilder.name(topicsProperties.getOnlineOrderTopic())
                .partitions(topicsProperties.getOnlineOrderTopicPartitions())
                .replicas(topicsProperties.getOnlineOrderTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic onlinePaymentTopic() {
        return TopicBuilder.name(topicsProperties.getOnlinePaymentTopic())
                .partitions(topicsProperties.getOnlinePaymentTopicPartitions())
                .replicas(topicsProperties.getOnlinePaymentTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic premiumPurchaseTopic() {
        return TopicBuilder.name(topicsProperties.getPremiumPurchaseTopic())
                .partitions(topicsProperties.getPremiumUserTopicPartitions())
                .replicas(topicsProperties.getPremiumPurchaseTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic premiumUserTopic() {
        return TopicBuilder.name(topicsProperties.getPremiumUserTopic())
                .partitions(topicsProperties.getPremiumUserTopicPartitions())
                .replicas(topicsProperties.getPremiumUserTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic subscriptionPurchaseTopic() {
        return TopicBuilder.name(topicsProperties.getSubscriptionPurchaseTopic())
                .partitions(topicsProperties.getSubscriptionPurchaseTopicPartitions())
                .replicas(topicsProperties.getSubscriptionPurchaseTopicReplicas())
                .build();
    }

    @Bean
    public NewTopic userSubscriptionTopic() {
        return TopicBuilder.name(topicsProperties.getUserSubscriptionTopic())
                .partitions(topicsProperties.getUserSubscriptionTopicPartitions())
                .replicas(topicsProperties.getUserSubscriptionTopicReplicas())
                .build();
    }
}
