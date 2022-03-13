package com.kat.kafkawebvotesstream.broker.stream;

import com.kat.ordersmodel.WebColorVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import java.time.LocalDateTime;

import static com.kat.kafkawebvotesstream.util.StreamsUtil.toEpochTimestamp;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
		WebColorVoteMessage webColorVoteMessage = (WebColorVoteMessage) consumerRecord.value();
		LocalDateTime voteDateTime = webColorVoteMessage.getVoteDateTime();
		return  voteDateTime != null ? toEpochTimestamp(voteDateTime) : consumerRecord.timestamp();
	}
}
