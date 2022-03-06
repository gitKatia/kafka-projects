package com.kat.feedbackdetailedratingstream.broker.stream;

import com.kat.feedbackdetailedratingstream.config.TopicsProperties;
import com.kat.ordersmodel.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@RequiredArgsConstructor
public class FeedbackDetailedRatingStream {

	private final TopicsProperties topicsProperties;

	@Bean
	public KStream<String, FeedbackMessage> kStreamFeedbackRating(StreamsBuilder builder) {

		Serde<String> stringSerDe = Serdes.String();
		JsonSerde<FeedbackMessage> feedbackJsonSerDe = new JsonSerde<>(FeedbackMessage.class);
		JsonSerde<FeedbackDetailedRatingMessage> feedbackRatingJsonSerDe = new JsonSerde<>(FeedbackDetailedRatingMessage.class);
		JsonSerde<FeedbackRatingStoreValue> feedbackRatingStoreValueJsonSerDe = new JsonSerde<>(FeedbackRatingStoreValue.class);

		KStream<String, FeedbackMessage> feedbackStream = builder.stream(topicsProperties.getFeedbackTopic(), Consumed.with(stringSerDe, feedbackJsonSerDe));
		feedbackStream.print(Printed.<String,FeedbackMessage>toSysOut().withLabel("Feedback Stream"));

		String feedbackRatingStateStoreName = "feedbackDetailedRatingStateStore";
		KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
		StoreBuilder<KeyValueStore<String, FeedbackRatingStoreValue>> storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerDe, feedbackRatingStoreValueJsonSerDe);

		builder.addStateStore(storeBuilder);

		KStream<String, FeedbackDetailedRatingMessage> feedbackDetailedRatingStream = feedbackStream
				.transformValues(() -> new FeedbackRatingTransformer(feedbackRatingStateStoreName),feedbackRatingStateStoreName);

		feedbackDetailedRatingStream.print(Printed.<String, FeedbackDetailedRatingMessage>toSysOut().withLabel("Feedback Detailed Rating Stream"));
		feedbackDetailedRatingStream.to(topicsProperties.getFeedbackDetailedRatingTopic(), Produced.with(stringSerDe, feedbackRatingJsonSerDe));

		return feedbackStream;
	}

}
