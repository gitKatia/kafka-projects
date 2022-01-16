package com.kat.kafkafeedbackonestreams.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-feedback-one-streams";
    private String feedbackTopic;
    private String feedbackOneGoodTopic;
    private int feedbackOneGoodTopicReplicas;
    private int feedbackOneGoodTopicPartitions;
}
