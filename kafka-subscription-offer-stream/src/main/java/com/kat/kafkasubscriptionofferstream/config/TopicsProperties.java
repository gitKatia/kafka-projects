package com.kat.kafkasubscriptionofferstream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-subscription-offer-stream";
    private String subscriptionPurchaseTopic;
    private String userSubscriptionTopic;
    private String subscriptionOfferTopic;
}
