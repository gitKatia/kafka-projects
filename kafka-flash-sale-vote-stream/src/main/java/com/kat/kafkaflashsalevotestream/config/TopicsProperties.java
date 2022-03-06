package com.kat.kafkaflashsalevotestream.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TopicsProperties.TOPICS_PREFIX)
@Data
public class TopicsProperties {
    static final String TOPICS_PREFIX = "kafka-flash-sale-votes-stream";
    private String flashSaleVotesTopic;
    private String flashSaleVotesUserItemTopic;
    private int flashSaleVotesUserItemTopicPartitions;
    private int flashSaleVotesUserItemTopicReplicas;
    private String flashSaleVotesCountTopic;
    private int flashSaleVotesCountTopicPartitions;
    private int flashSaleVotesCountTopicReplicas;
}
