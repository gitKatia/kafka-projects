package com.kat.kafkastreamsone.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-one";
    private String ordersTopic;
    private String ordersPatternOneTopic;
    private int  ordersPatternOneTopicReplicas;
    private int  ordersPatternOneTopicPartitions;
    private String ordersRewardOneTopic;
    private int  ordersRewardOneTopicReplicas;
    private int  ordersRewardOneTopicPartitions;
    private String ordersStorageOneTopic;
    private int  ordersStorageOneTopicReplicas;
    private int  ordersStorageOneTopicPartitions;
}
