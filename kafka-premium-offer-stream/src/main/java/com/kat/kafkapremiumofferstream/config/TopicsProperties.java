package com.kat.kafkapremiumofferstream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-premium-offer-stream";
    private String premiumPurchaseTopic;
    private String premiumUserTopic;
    private String premiumOfferTopic;
    private int premiumOfferTopicPartitions;
    private int premiumOfferTopicReplicas;
}
