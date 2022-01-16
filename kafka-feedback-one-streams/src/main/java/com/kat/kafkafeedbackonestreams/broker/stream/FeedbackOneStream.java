package com.kat.kafkafeedbackonestreams.broker.stream;

import com.kat.kafkafeedbackonestreams.config.TopicsProperties;
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

import static com.kat.kafkafeedbackonestreams.util.StreamUtils.goodWordsKeyValueMapper;

@Configuration
@RequiredArgsConstructor
public class FeedbackOneStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String,String> kStreamFeedback(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackJsonSerDe = new JsonSerde<>(FeedbackMessage.class);

        // flatMap transforms each record of the input stream into one or more records in the output stream through a KeyValueMapper
        KStream<String, String> goodFeedbackStream = builder.stream(topicsProperties.getFeedbackTopic(), Consumed.with(stringSerDe, feedbackJsonSerDe))
                .flatMap(goodWordsKeyValueMapper());

        goodFeedbackStream.to(topicsProperties.getFeedbackOneGoodTopic());
        goodFeedbackStream.print(Printed.<String,String>toSysOut().withLabel("Good Feedback One Stream"));

        return goodFeedbackStream;
    }
}
