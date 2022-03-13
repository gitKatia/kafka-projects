package com.kat.kafkawebvotesstream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-web-votes-stream";
    private String webLayoutVoteTopic;
    private String webColorVoteTopic;
    private String webColorLayoutTopic;
}
