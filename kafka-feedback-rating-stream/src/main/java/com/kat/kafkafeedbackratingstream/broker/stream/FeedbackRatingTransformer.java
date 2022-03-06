package com.kat.kafkafeedbackratingstream.broker.stream;

import com.kat.ordersmodel.FeedbackMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class FeedbackRatingTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingMessage> {

	private final String stateStoreName;
	private ProcessorContext processorContext;
	private KeyValueStore<String, FeedbackRatingStoreValue> ratingStateStore;

	public FeedbackRatingTransformer(String stateStoreName) {
		this.stateStoreName = stateStoreName;
	}

	@Override
	public void init(ProcessorContext context) {
		this.processorContext = context;
		this.ratingStateStore = this.processorContext.getStateStore(stateStoreName);
	}

	@Override
	public FeedbackRatingMessage transform(FeedbackMessage feedbackMessage) {
		// trying to retrieve the value for the location in the message from state store, if it exists already
		FeedbackRatingStoreValue storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
				.orElse(new FeedbackRatingStoreValue());

		// inserting or updating the store value
		long ratingsSum = storeValue.getRatingsSum() + feedbackMessage.getRating();
		long ratingsCount = storeValue.getRatingsCount() + 1;

		storeValue.setRatingsSum(ratingsSum);
		storeValue.setRatingsCount(ratingsCount);

		// put store to state store
		ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

		// build branch rating
		BigDecimal bd = BigDecimal.valueOf((double) ratingsSum / ratingsCount);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		double averageRating = bd.doubleValue();
		FeedbackRatingMessage branchRating = FeedbackRatingMessage.builder()
				.location(feedbackMessage.getLocation())
				.averageRating(averageRating)
				.build();

		return branchRating;
	}

	@Override
	public void close() {
	}
}
