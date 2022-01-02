package com.kat.kafkastreamstwo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-two";
    private String ordersTopic;
    private String ordersPatternTwoPlasticTopic;
    private int  ordersPatternTwoPlasticTopicReplicas;
    private int  ordersPatternTwoPlasticTopicPartitions;
    private String ordersPatternTwoNoPlasticTopic;
    private int  ordersPatternTwoNoPlasticTopicReplicas;
    private int  ordersPatternTwoNoPlasticTopicPartitions;
    private String ordersRewardTwoTopic;
    private int  ordersRewardTwoTopicReplicas;
    private int  ordersRewardTwoTopicPartitions;
    private String ordersStorageTwoTopic;
    private int  ordersStorageTwoTopicReplicas;
    private int  ordersStorageTwoTopicPartitions;
}
