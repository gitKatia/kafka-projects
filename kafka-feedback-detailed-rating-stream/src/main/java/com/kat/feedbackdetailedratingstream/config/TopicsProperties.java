package com.kat.feedbackdetailedratingstream.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-feedback-detailed-rating-stream";
    private String feedbackTopic;
    private String feedbackDetailedRatingTopic;
    private int feedbackDetailedRatingTopicPartitions;
    private int feedbackDetailedRatingTopicReplicas;
}
