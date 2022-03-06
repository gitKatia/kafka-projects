package com.kat.kafkaflashsalevotestream.broker.stream;

import com.kat.ordersmodel.FlashSaleVoteMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class FlashSaleVotesWindowValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

	private final long voteStartTime;
	private final long voteEndTime;
	private ProcessorContext processorContext;

	public FlashSaleVotesWindowValueTransformer(LocalDateTime voteStart, LocalDateTime voteEnd) {
		this.voteStartTime = toEpochTimestamp(voteStart);
		this.voteEndTime = toEpochTimestamp(voteEnd);
	}

	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
	}

	@Override
	public FlashSaleVoteMessage transform(FlashSaleVoteMessage flashSaleVoteMessage) {
		// extracting timestamp from record (timestamp is set from producer)
		long recordTime = processorContext.timestamp();

		return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? flashSaleVoteMessage : null;
	}

	@Override
	public void close() {
		// check what to do here
	}

	private long toEpochTimestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
