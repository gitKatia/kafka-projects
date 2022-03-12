package com.kat.kafkaorderpaymentleftjoinstream.broker.stream;

import com.kat.kafkaorderpaymentleftjoinstream.utils.StreamsUtil;
import com.kat.ordersmodel.OnlinePaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.util.Optional;

public class OnlinePaymentTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		OnlinePaymentMessage onlinePaymentMessage = (OnlinePaymentMessage) record.value();

		return Optional.ofNullable(onlinePaymentMessage.getPaymentDateTime())
				.map(StreamsUtil::toEpochTimestamp)
				.orElseGet(() -> record.timestamp());
	}
}
