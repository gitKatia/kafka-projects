package com.kat.kafkawebvotesstream.broker.stream;

import com.kat.ordersmodel.WebLayoutVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;

import static com.kat.kafkawebvotesstream.util.StreamsUtil.toEpochTimestamp;

public class WebLayoutVoteTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
		WebLayoutVoteMessage webLayoutVoteMessage = (WebLayoutVoteMessage) consumerRecord.value();
		LocalDateTime voteDateTime = webLayoutVoteMessage.getVoteDateTime();
		return  voteDateTime != null ? toEpochTimestamp(voteDateTime) : consumerRecord.timestamp();
	}
}
