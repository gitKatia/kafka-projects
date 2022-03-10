package com.kat.kafkainventoryreducestream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-inventory-reduce-stream";
    private String inventoryTopic;
    private String inventoryTotalTopic;
    private int inventoryTotalTopicReplicas;
    private int inventoryTotalTopicPartitions;
}
