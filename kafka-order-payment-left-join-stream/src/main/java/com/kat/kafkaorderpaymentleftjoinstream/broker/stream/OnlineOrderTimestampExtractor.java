package com.kat.kafkaorderpaymentleftjoinstream.broker.stream;

import com.kat.kafkaorderpaymentleftjoinstream.utils.StreamsUtil;
import com.kat.ordersmodel.OnlineOrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.util.Optional;

public class OnlineOrderTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		OnlineOrderMessage onlineOrderMessage = (OnlineOrderMessage) record.value();

		return Optional.ofNullable(onlineOrderMessage.getOrderDateTime())
				.map(StreamsUtil::toEpochTimestamp)
				.orElseGet(() -> record.timestamp());
	}
}
