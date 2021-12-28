package com.kat.kafkastreamsone.broker.stream;

import com.kat.kafkastreamsone.broker.message.OrderPatternMessage;
import com.kat.kafkastreamsone.broker.message.OrderRewardMessage;
import com.kat.kafkastreamsone.config.TopicsProperties;
import com.kat.kafkastreamsone.util.StreamUtils;
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

import static com.kat.kafkastreamsone.util.StreamUtils.isLargeQuantity;

@Configuration
@RequiredArgsConstructor
public class OrderOneStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OrderMessage> kStreamOne(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<OrderMessage> orderMessageJsonSerDe = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternJsonSerDe = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardJsonSerDe = new JsonSerde<>(OrderRewardMessage.class);

        // First transformation
        KStream<String, OrderMessage> maskedOrderStream = builder
                .stream(topicsProperties.getOrdersTopic(), Consumed.with(stringSerDe, orderMessageJsonSerDe))
                .mapValues(StreamUtils::maskCreditCard);

        // Second transformation and first sink
        KStream<String, OrderPatternMessage> orderPatternStream = maskedOrderStream
                .mapValues(StreamUtils::mapToOrderPattern);
        orderPatternStream .to(topicsProperties.getOrdersPatternOneTopic(), Produced.with(stringSerDe, orderPatternJsonSerDe));
        orderPatternStream.print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("Order Pattern Stream"));

        // Second transformation and second sink
        KStream<String, OrderRewardMessage>  orderRewardStream = maskedOrderStream.filter(isLargeQuantity())
                .mapValues(StreamUtils::mapToOrderReward);
        orderRewardStream.to(topicsProperties.getOrdersRewardOneTopic(), Produced.with(stringSerDe, orderRewardJsonSerDe));
        orderRewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("Order Reward Stream"));

        // No transformation and third sink
        maskedOrderStream.to(topicsProperties.getOrdersStorageOneTopic(), Produced.with(stringSerDe, orderMessageJsonSerDe));
        maskedOrderStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));

        return maskedOrderStream;
    }
}
