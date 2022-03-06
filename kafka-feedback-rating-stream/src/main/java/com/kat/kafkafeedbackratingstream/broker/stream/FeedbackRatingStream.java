package com.kat.kafkafeedbackratingstream.broker.stream;

import com.kat.kafkafeedbackratingstream.config.TopicsProperties;
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
public class FeedbackRatingStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedbackRating(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackJsonSerDe = new JsonSerde<>(FeedbackMessage.class);
        JsonSerde<FeedbackRatingMessage> feedbackRatingJsonSerDe = new JsonSerde<>(FeedbackRatingMessage.class);
        JsonSerde<FeedbackRatingStoreValue> feedbackRatingStoreValueJsonSerDe = new JsonSerde<>(FeedbackRatingStoreValue.class);

        KStream<String, FeedbackMessage> feedbackStream = builder.stream(topicsProperties.getFeedbackTopic(), Consumed.with(stringSerDe, feedbackJsonSerDe));
        feedbackStream.print(Printed.<String,FeedbackMessage>toSysOut().withLabel("Feedback Stream"));

        String feedbackRatingStateStoreName = "feedbackRatingStateStore";
        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        // building the store
        StoreBuilder<KeyValueStore<String,FeedbackRatingStoreValue >> storeBuilder =
                Stores.keyValueStoreBuilder(storeSupplier, stringSerDe, feedbackRatingStoreValueJsonSerDe);

        builder.addStateStore(storeBuilder);

        KStream<String, FeedbackRatingMessage> feedbackRatingStream = feedbackStream
                .transformValues(() -> new FeedbackRatingTransformer(feedbackRatingStateStoreName), feedbackRatingStateStoreName);
        feedbackRatingStream.print(Printed.<String,FeedbackRatingMessage>toSysOut().withLabel("Feedback Rating Stream"));
        feedbackRatingStream.to("t.commodity.feedback.rating-one", Produced.with(stringSerDe, feedbackRatingJsonSerDe));

        return feedbackStream;
    }
}
