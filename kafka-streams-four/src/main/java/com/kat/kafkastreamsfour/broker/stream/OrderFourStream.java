package com.kat.kafkastreamsfour.broker.stream;

import com.kat.kafkastreamsfour.broker.message.OrderPatternMessage;
import com.kat.kafkastreamsfour.broker.message.OrderRewardMessage;
import com.kat.kafkastreamsfour.config.TopicsProperties;
import com.kat.kafkastreamsfour.util.StreamUtils;
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
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkastreamsfour.util.StreamUtils.*;


@Configuration
@RequiredArgsConstructor
public class OrderFourStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OrderMessage> kStreamFour(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<OrderMessage> orderMessageJsonSerDe = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternJsonSerDe = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardJsonSerDe = new JsonSerde<>(OrderRewardMessage.class);

        // First transformation
        KStream<String, OrderMessage> maskedOrderStream = builder
                .stream(topicsProperties.getOrdersTopic(), Consumed.with(stringSerDe, orderMessageJsonSerDe))
                .mapValues(StreamUtils::maskCreditCard);

        // Second transformation
        Produced<String,OrderPatternMessage> producedWith = Produced.with(stringSerDe, orderPatternJsonSerDe);
        // Arguments for branch method: predicate and consumer
        // Argument for defaultBranch method: consumer
        // onTopOf method: specifies the stream
        new KafkaStreamBrancher<String, OrderPatternMessage>()
                .branch(isPlastic(), kStream -> kStream.to(topicsProperties.getOrdersPatternFourPlasticTopic(), producedWith))
                .defaultBranch(kStream -> kStream.to(topicsProperties.getOrdersPatternFourNoPlasticTopic(), producedWith))
                .onTopOf(maskedOrderStream.mapValues(StreamUtils::mapToOrderPattern))
                .print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("Order Pattern Stream"));

        // Second transformation and second sink
        KStream<String, OrderRewardMessage>  orderRewardStream = maskedOrderStream.filter(isLargeQuantity())
                .filterNot(isCheap())
                .map(mapToOrderRewardChangeKey());
        orderRewardStream.to(topicsProperties.getOrdersRewardFourTopic(), Produced.with(stringSerDe, orderRewardJsonSerDe));
        orderRewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("Order Reward Stream"));

        // third sink
        // Generating new key
        KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(generateStorageKey());
        storageStream.to(topicsProperties.getOrdersStorageFourTopic(), Produced.with(stringSerDe, orderMessageJsonSerDe));
        storageStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Storage Order Stream"));

        return maskedOrderStream;
    }
}
