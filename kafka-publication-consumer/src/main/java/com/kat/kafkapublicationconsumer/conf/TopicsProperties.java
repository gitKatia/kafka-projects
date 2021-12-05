package com.kat.kafkapublicationconsumer.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-publication-consumer";
    private String articleTopic;
    private String bookTopic;
}
