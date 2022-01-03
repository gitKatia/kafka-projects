package com.kat.kafkastreamsthree.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-two";
    private String ordersTopic;
    private String ordersPatternThreePlasticTopic;
    private int  ordersPatternThreePlasticTopicReplicas;
    private int  ordersPatternThreePlasticTopicPartitions;
    private String ordersPatternThreeNoPlasticTopic;
    private int  ordersPatternThreeNoPlasticTopicReplicas;
    private int  ordersPatternThreeNoPlasticTopicPartitions;
    private String ordersRewardThreeTopic;
    private int  ordersRewardThreeTopicReplicas;
    private int  ordersRewardThreeTopicPartitions;
    private String ordersStorageThreeTopic;
    private int  ordersStorageThreeTopicReplicas;
    private int  ordersStorageThreeTopicPartitions;
}
