package com.kat.kafkatimestampextractorinventorystream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-timestamp-extractor-inventory-stream";
    private String inventoryTopic;
    private String inventoryTimestampTopic;
    private int inventoryTimestampTopicReplicas;
    private int inventoryTimestampTopicPartitions;
}
