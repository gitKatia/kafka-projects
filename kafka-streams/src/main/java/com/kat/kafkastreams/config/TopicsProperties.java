package com.kat.kafkastreams.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-streams";
    private String ordersTopic;
    private String maskedOrdersTopic;
    private int maskedOrdersTopicReplicas;
    private int maskedOrdersTopicPartitions;
}
