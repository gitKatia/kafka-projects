package com.kat.kafkaflashsalevotestream.broker.stream;

import com.kat.kafkaflashsalevotestream.config.TopicsProperties;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class FlashSaleVoteWindowStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, String> kStreamFlashSaleVote(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<FlashSaleVoteMessage> flashSaleVoteJsonSerDe = new JsonSerde<>(FlashSaleVoteMessage.class);

        LocalDateTime voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));

        KStream<String, String> flashSaleVoteWindowStream = builder
                .stream(topicsProperties.getFlashSaleVotesTopic(), Consumed.with(stringSerDe, flashSaleVoteJsonSerDe))
                // here we are transforming and filtering out records out of the vote window
                .transformValues(() -> new FlashSaleVotesWindowValueTransformer(voteStart, voteEnd))
                .filter((key, transformedValue) -> transformedValue != null)
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));
        flashSaleVoteWindowStream.print(Printed.<String,String>toSysOut().withLabel("Flash sale votes window Stream"));
        // the stream goes to a topic as (customerId, itemName)
        flashSaleVoteWindowStream.to(topicsProperties.getFlashSaleVotesUserItemTopic());

        // We want to have the possibility to update the item voted by a customer and so we use a table
        KStream<String, Long> flashSaleVoteCountStream =  builder.table(topicsProperties.getFlashSaleVotesUserItemTopic(), Consumed.with(stringSerDe, stringSerDe))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count()
                .toStream();
        flashSaleVoteCountStream.print(Printed.<String,Long>toSysOut().withLabel("Flash sale votes count window Stream"));
        flashSaleVoteCountStream.to(topicsProperties.getFlashSaleVotesCountTopic(), Produced.with(stringSerDe, Serdes.Long()));

        return flashSaleVoteWindowStream;
    }
}
