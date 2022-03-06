package com.kat.kafkafeedbackratingstream.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-feedback-rating-stream";
    private String feedbackTopic;
    private String feedbackRatingTopic;
    private int feedbackRatingTopicPartitions;
    private int feedbackRatingTopicReplicas;
}
