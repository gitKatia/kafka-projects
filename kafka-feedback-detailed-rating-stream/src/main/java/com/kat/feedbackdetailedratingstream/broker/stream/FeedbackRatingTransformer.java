package com.kat.feedbackdetailedratingstream.broker.stream;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import com.kat.ordersmodel.FeedbackMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class FeedbackRatingTransformer implements ValueTransformer<FeedbackMessage, FeedbackDetailedRatingMessage> {

	private ProcessorContext processorContext;
	private final String stateStoreName;
	private KeyValueStore<String, FeedbackRatingStoreValue> ratingStateStore;

	public FeedbackRatingTransformer(String stateStoreName) {
		this.stateStoreName = stateStoreName;
	}

	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
		this.ratingStateStore = this.processorContext.getStateStore(this.stateStoreName);
	}

	@Override
	public FeedbackDetailedRatingMessage transform(FeedbackMessage feedbackMessage) {
		FeedbackRatingStoreValue storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
				.orElse(new FeedbackRatingStoreValue());
		// contains for each rating value (1,2,3,4,5) the number of ratings
		Map<Integer, Long> ratingMap = storeValue.getRatingMap();

		long currentRatingCount = Optional.ofNullable(ratingMap.get(feedbackMessage.getRating())).orElse(0l);
		long newRatingCount = currentRatingCount + 1;
		ratingMap.put(feedbackMessage.getRating(), newRatingCount);
		ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

		// send this message to sink topic
		FeedbackDetailedRatingMessage branchRating = FeedbackDetailedRatingMessage.builder()
				.location(feedbackMessage.getLocation())
				.ratingMap(ratingMap)
				.averageRating(getAverage(ratingMap))
				.build();

		return branchRating;
	}

	private double getAverage(Map<Integer, Long> ratingMap) {

		long ratingsSum = ratingMap.entrySet().stream()
				.map(e -> e.getKey()*e.getValue())
				.reduce((a,b)-> a + b)
				.orElse(0L);
		long ratingsCount = ratingMap.entrySet().stream()
				.map(e -> e.getValue())
				.reduce((a, b) -> a + b)
				.orElse(0L);
		if (ratingsCount == 0) {
			return BigDecimal.ZERO.doubleValue();
		}

		BigDecimal bd = BigDecimal.valueOf((double) ratingsSum / ratingsCount);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void close() {
	}
}
