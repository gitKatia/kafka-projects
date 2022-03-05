package com.kat.kafkaflashsalevotesstream.broker.stream;

import com.kat.kafkaflashsalevotesstream.config.TopicsProperties;
import com.kat.ordersmodel.FeedbackMessage;
import com.kat.ordersmodel.FlashSaleVoteMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@RequiredArgsConstructor
public class FlashSaleVotesStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, String> kStreamFlashSaleVote(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FlashSaleVoteMessage> flashSaleVoteJsonSerDe = new JsonSerde<>(FlashSaleVoteMessage.class);

        KStream<String, String> flashSaleVoteStream = builder
                .stream(topicsProperties.getFlashSaleVotesTopic(), Consumed.with(stringSerDe, flashSaleVoteJsonSerDe))
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));
        flashSaleVoteStream.print(Printed.<String,String>toSysOut().withLabel("Flash sale votes Stream"));
        // the stream goes to a topic as (customerId, itemName)
        flashSaleVoteStream.to(topicsProperties.getFlashSaleVotesUserItemTopic());

        // We want to have the possibility to update the item voted by a customer and so we use a table
        KStream<String, Long> flashSaleVoteCountStream =  builder.table(topicsProperties.getFlashSaleVotesUserItemTopic(), Consumed.with(stringSerDe, stringSerDe))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count()
                .toStream();
        flashSaleVoteCountStream.print(Printed.<String,Long>toSysOut().withLabel("Flash sale votes count Stream"));
        flashSaleVoteCountStream.to(topicsProperties.getFlashSaleVotesCountTopic(), Produced.with(stringSerDe, Serdes.Long()));

        return flashSaleVoteStream;
    }
}
