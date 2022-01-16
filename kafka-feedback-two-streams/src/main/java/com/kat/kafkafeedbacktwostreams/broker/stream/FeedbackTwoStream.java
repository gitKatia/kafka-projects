package com.kat.kafkafeedbacktwostreams.broker.stream;

import com.kat.kafkafeedbacktwostreams.config.TopicsProperties;
import com.kat.ordersmodel.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkafeedbacktwostreams.util.StreamUtils.*;


@Configuration
@RequiredArgsConstructor
public class FeedbackTwoStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String,FeedbackMessage> kStreamFeedback(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackJsonSerDe = new JsonSerde<>(FeedbackMessage.class);

        KStream<String, FeedbackMessage> sourceStream = builder.stream(topicsProperties.getFeedbackTopic(), Consumed.with(stringSerDe, feedbackJsonSerDe));

        // flatMap transforms each record of the input stream into one or more records in the output stream through a KeyValueMapper
        KStream<String, String>[] feedbackStreams = sourceStream
                .flatMap(words())
                .branch(isGoodWord(), isBadWord());

        feedbackStreams[0].print(Printed.<String,String>toSysOut().withLabel("Good Feedback Two Stream"));
        KStream<String, Long> goodCounts = feedbackStreams[0].groupByKey(Grouped.with(stringSerDe, stringSerDe)).count().toStream();
        feedbackStreams[0].to(topicsProperties.getFeedbackTwoGoodTopic());

        goodCounts.to(topicsProperties.getFeedbackTwoGoodCountTopic());
        goodCounts.print(Printed.<String,Long>toSysOut().withLabel("Good Feedback Two Count Stream"));

        KStream<String, Long> badCounts = feedbackStreams[1].groupByKey().count().toStream();
        feedbackStreams[1].to(topicsProperties.getFeedbackTwoGoodTopic());
        feedbackStreams[1].print(Printed.<String,String>toSysOut().withLabel("Bad Feedback Two Stream"));

        badCounts.to(topicsProperties.getFeedbackTwoBadCountTopic());
        badCounts.print(Printed.<String,Long>toSysOut().withLabel("Bad Feedback Two Count Stream"));

        return sourceStream;
    }
}
