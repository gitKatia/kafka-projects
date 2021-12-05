package com.kat.kafkarebalancedproducer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-rebalanced-producer";
    private String rebalancedTopic;
}
