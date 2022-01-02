package com.kat.kafkastreamstwo.broker.stream;

import com.kat.kafkastreamstwo.broker.message.OrderPatternMessage;
import com.kat.kafkastreamstwo.broker.message.OrderRewardMessage;
import com.kat.kafkastreamstwo.config.TopicsProperties;
import com.kat.kafkastreamstwo.util.StreamUtils;
import com.kat.ordersmodel.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkastreamstwo.util.StreamUtils.*;

@Configuration
@RequiredArgsConstructor
public class OrderTwoStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OrderMessage> kStreamTwo(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<OrderMessage> orderMessageJsonSerDe = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternJsonSerDe = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardJsonSerDe = new JsonSerde<>(OrderRewardMessage.class);

        // First transformation
        KStream<String, OrderMessage> maskedOrderStream = builder
                .stream(topicsProperties.getOrdersTopic(), Consumed.with(stringSerDe, orderMessageJsonSerDe))
                .mapValues(StreamUtils::maskCreditCard);

        // Second transformation and first two sinks
        KStream<String, OrderPatternMessage>[] orderPatternStream = maskedOrderStream
                .mapValues(StreamUtils::mapToOrderPattern)
                .branch(isPlastic(), (k, v) -> true);

        int plasticIndex = 0;
        int notPlasticIndex = 1;

        orderPatternStream[plasticIndex].to(topicsProperties.getOrdersPatternTwoPlasticTopic(),
                Produced.with(stringSerDe, orderPatternJsonSerDe));
        orderPatternStream[notPlasticIndex].to(topicsProperties.getOrdersPatternTwoNoPlasticTopic(),
                Produced.with(stringSerDe, orderPatternJsonSerDe));


        orderPatternStream[plasticIndex].print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("Order Pattern Stream Plastic"));
        orderPatternStream[notPlasticIndex].print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("Order Pattern Stream Not Plastic"));

        // Second transformation and second sink
        KStream<String, OrderRewardMessage>  orderRewardStream = maskedOrderStream.filter(isLargeQuantity())
                .filterNot(isCheap())
                .mapValues(StreamUtils::mapToOrderReward);
        orderRewardStream.to(topicsProperties.getOrdersRewardTwoTopic(), Produced.with(stringSerDe, orderRewardJsonSerDe));
        orderRewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("Order Reward Stream"));

        // third sink
        // Generating new key
        KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(generateStorageKey());
        storageStream.to(topicsProperties.getOrdersStorageTwoTopic(), Produced.with(stringSerDe, orderMessageJsonSerDe));
        storageStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));

        return maskedOrderStream;
    }
}
