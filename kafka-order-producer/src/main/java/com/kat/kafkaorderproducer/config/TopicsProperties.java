package com.kat.kafkaorderproducer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-order-producer";
    private String ordersTopic;
    private int ordersTopicReplicas;
    private int ordersTopicPartitions;
    private String ordersReplyToTopic;
    private int ordersReplyToTopicReplicas;
    private int ordersReplyToTopicPartitions;
    private String promotionsTopic;
    private int promotionsTopicReplicas;
    private int promotionsTopicPartitions;
    private String discountsTopic;
    private int discountsTopicReplicas;
    private int discountsTopicPartitions;
    private String feedbackTopic;
    private int feedbackTopicReplicas;
    private int feedbackTopicPartitions;
    private String flashSaleVotesTopic;
    private int flashSaleVotesTopicReplicas;
    private int flashSaleVotesTopicPartitions;
    private String inventoryTopic;
    private int inventoryTopicReplicas;
    private int inventoryTopicPartitions;
    private String onlineOrderTopic;
    private int onlineOrderTopicReplicas;
    private int onlineOrderTopicPartitions;
    private String onlinePaymentTopic;
    private int onlinePaymentTopicReplicas;
    private int onlinePaymentTopicPartitions;
    private String premiumPurchaseTopic;
    private int premiumPurchaseTopicReplicas;
    private int premiumPurchaseTopicPartitions;
    private String premiumUserTopic;
    private int premiumUserTopicReplicas;
    private int premiumUserTopicPartitions;
    private String subscriptionPurchaseTopic;
    private int subscriptionPurchaseTopicReplicas;
    private int subscriptionPurchaseTopicPartitions;
    private String userSubscriptionTopic;
    private int userSubscriptionTopicReplicas;
    private int userSubscriptionTopicPartitions;
    private String webLayoutVoteTopic;
    private int webLayoutVoteTopicReplicas;
    private int webLayoutVoteTopicPartitions;
    private String webColorVoteTopic;
    private int webColorVoteTopicReplicas;
    private int webColorVoteTopicPartitions;
}
