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
}