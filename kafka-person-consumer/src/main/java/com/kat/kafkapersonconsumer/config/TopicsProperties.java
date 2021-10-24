package com.kat.kafkapersonconsumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-person-consumer";
    private static final int AGE_FILTER_DEFAULT = 10;
    private String personTopic;
    private int ageFilter = AGE_FILTER_DEFAULT;
}
