package com.kat.kafkafeedbackthreestreams.broker.stream;

import com.kat.kafkafeedbackthreestreams.config.TopicsProperties;
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

import static com.kat.kafkafeedbackthreestreams.util.StreamUtils.*;


@Configuration
@RequiredArgsConstructor
public class FeedbackThreeStream {

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

        feedbackStreams[0].print(Printed.<String,String>toSysOut().withLabel("Good Feedback Three Stream"));
        KStream<String, Long> goodCounts = feedbackStreams[0].groupByKey().count().toStream();
        feedbackStreams[0].to(topicsProperties.getFeedbackThreeGoodTopic());

        goodCounts.to(topicsProperties.getFeedbackThreeGoodCountTopic());
        goodCounts.print(Printed.<String,Long>toSysOut().withLabel("Good Feedback Three Count Stream"));

        KStream<String, Long> badCounts = feedbackStreams[1].groupByKey().count().toStream();
        feedbackStreams[1].to(topicsProperties.getFeedbackThreeBadTopic());
        feedbackStreams[1].print(Printed.<String,String>toSysOut().withLabel("Bad Feedback Three Stream"));

        badCounts.to(topicsProperties.getFeedbackThreeBadCountTopic());
        badCounts.print(Printed.<String,Long>toSysOut().withLabel("Bad Feedback Three Count Stream"));

        KStream<String, Long> goodCountsOverall = feedbackStreams[0].groupBy((key, value) -> value).count().toStream();
        goodCountsOverall.to(topicsProperties.getFeedbackThreeGoodCountOverallTopic());
        goodCountsOverall.print(Printed.<String,Long>toSysOut().withLabel("Good Feedback Three Count Stream overall"));

        KStream<String, Long> badCountsOverall = feedbackStreams[1].groupBy((key, value) -> value).count().toStream();
        badCountsOverall.to(topicsProperties.getFeedbackThreeBadCountOverallTopic());
        badCountsOverall.print(Printed.<String,Long>toSysOut().withLabel("Bad Feedback Three Count Stream overall"));

        return sourceStream;
    }
}
