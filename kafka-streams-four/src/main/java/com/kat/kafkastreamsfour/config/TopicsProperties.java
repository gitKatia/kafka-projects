package com.kat.kafkastreamsfour.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-four";
    private String ordersTopic;
    private String ordersPatternFourPlasticTopic;
    private int  ordersPatternFourPlasticTopicReplicas;
    private int  ordersPatternFourPlasticTopicPartitions;
    private String ordersPatternFourNoPlasticTopic;
    private int  ordersPatternFourNoPlasticTopicReplicas;
    private int  ordersPatternFourNoPlasticTopicPartitions;
    private String ordersRewardFourTopic;
    private int  ordersRewardFourTopicReplicas;
    private int  ordersRewardFourTopicPartitions;
    private String ordersStorageFourTopic;
    private int  ordersStorageFourTopicReplicas;
    private int  ordersStorageFourTopicPartitions;
}
