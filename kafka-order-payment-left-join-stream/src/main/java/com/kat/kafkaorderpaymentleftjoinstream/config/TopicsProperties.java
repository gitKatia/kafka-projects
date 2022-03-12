package com.kat.kafkaorderpaymentleftjoinstream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-order-payment-left-join-stream";
    private String onlineOrderTopic;
    private String onlinePaymentTopic;
    private String onlineOrderPaymentTopic;
    private int onlineOrderPaymentTopicPartitions;
    private int onlineOrderPaymentTopicReplicas;
}
