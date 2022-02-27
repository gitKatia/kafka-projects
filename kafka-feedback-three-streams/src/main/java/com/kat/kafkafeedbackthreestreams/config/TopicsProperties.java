package com.kat.kafkafeedbackthreestreams.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-feedback-three-streams";
    private String feedbackTopic;
    private String feedbackThreeGoodTopic;
    private int feedbackThreeGoodTopicReplicas;
    private int feedbackThreeGoodTopicPartitions;
    private String feedbackThreeGoodCountTopic;
    private int feedbackThreeGoodCountTopicReplicas;
    private int feedbackThreeGoodCountTopicPartitions;
    private String feedbackThreeGoodCountOverallTopic;
    private int feedbackThreeGoodCountOverallTopicReplicas;
    private int feedbackThreeGoodCountOverallTopicPartitions;
    private String feedbackThreeBadTopic;
    private int feedbackThreeBadTopicReplicas;
    private int feedbackThreeBadTopicPartitions;
    private String feedbackThreeBadCountTopic;
    private int feedbackThreeBadCountTopicReplicas;
    private int feedbackThreeBadCountTopicPartitions;
    private String feedbackThreeBadCountOverallTopic;
    private int feedbackThreeBadCountOverallTopicReplicas;
    private int feedbackThreeBadCountOverallTopicPartitions;
}
