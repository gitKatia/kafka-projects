package com.kat.kafkastreamsfive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-five";
    private String ordersTopic;
    private String ordersPatternFivePlasticTopic;
    private int  ordersPatternFivePlasticTopicReplicas;
    private int  ordersPatternFivePlasticTopicPartitions;
    private String ordersPatternFiveNoPlasticTopic;
    private int  ordersPatternFiveNoPlasticTopicReplicas;
    private int  ordersPatternFiveNoPlasticTopicPartitions;
    private String ordersRewardFiveTopic;
    private int  ordersRewardFiveTopicReplicas;
    private int  ordersRewardFiveTopicPartitions;
    private String ordersStorageFiveTopic;
    private int  ordersStorageFiveTopicReplicas;
    private int  ordersStorageFiveTopicPartitions;
}
