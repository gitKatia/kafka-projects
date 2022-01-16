package com.kat.kafkafeedbacktwostreams.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-feedback-two-streams";
    private String feedbackTopic;
    private String feedbackTwoGoodTopic;
    private int feedbackTwoGoodTopicReplicas;
    private int feedbackTwoGoodTopicPartitions;
    private String feedbackTwoGoodCountTopic;
    private int feedbackTwoGoodCountTopicReplicas;
    private int feedbackTwoGoodCountTopicPartitions;
    private String feedbackTwoBadTopic;
    private int feedbackTwoBadTopicReplicas;
    private int feedbackTwoBadTopicPartitions;
    private String feedbackTwoBadCountTopic;
    private int feedbackTwoBadCountTopicReplicas;
    private int feedbackTwoBadCountTopicPartitions;
}
