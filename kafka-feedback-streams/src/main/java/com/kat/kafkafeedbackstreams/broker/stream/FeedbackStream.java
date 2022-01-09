package com.kat.kafkafeedbackstreams.broker.stream;

import com.kat.kafkafeedbackstreams.config.TopicsProperties;
import com.kat.ordersmodel.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkafeedbackstreams.util.StreamUtils.goodWordsMapper;

@Configuration
@RequiredArgsConstructor
public class FeedbackStream {
    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String,String> kStreamFeedback(StreamsBuilder builder) {
       Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackJsonSerDe = new JsonSerde<>(FeedbackMessage.class);

        KStream<String, String> goodFeedbackStream = builder.stream(topicsProperties.getFeedbackTopic(), Consumed.with(stringSerDe, feedbackJsonSerDe))
                .flatMapValues(goodWordsMapper());
        goodFeedbackStream.to(topicsProperties.getFeedbackGoodTopic());
        goodFeedbackStream.print(Printed.<String,String>toSysOut().withLabel("Good Feedback Stream"));

        return goodFeedbackStream;
    }
}
