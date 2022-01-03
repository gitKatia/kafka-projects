package com.kat.kafkastreamssix.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams-six";
    private String ordersTopic;
    private String ordersPatternSixPlasticTopic;
    private int  ordersPatternSixPlasticTopicReplicas;
    private int  ordersPatternSixPlasticTopicPartitions;
    private String ordersPatternSixNoPlasticTopic;
    private int  ordersPatternSixNoPlasticTopicReplicas;
    private int  ordersPatternSixNoPlasticTopicPartitions;
    private String ordersRewardSixTopic;
    private int  ordersRewardSixTopicReplicas;
    private int  ordersRewardSixTopicPartitions;
    private String ordersStorageSixTopic;
    private int  ordersStorageSixTopicReplicas;
    private int  ordersStorageSixTopicPartitions;
    private String ordersFraudSixTopic;
    private int  ordersFraudSixTopicReplicas;
    private int  ordersFraudSixTopicPartitions;
}
