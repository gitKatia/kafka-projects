package com.kat.kafkastreamssix.broker.stream;

import com.kat.kafkastreamssix.broker.message.OrderPatternMessage;
import com.kat.kafkastreamssix.broker.message.OrderRewardMessage;
import com.kat.kafkastreamssix.config.TopicsProperties;
import com.kat.kafkastreamssix.util.StreamUtils;
import com.kat.ordersmodel.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkastreamssix.util.StreamUtils.*;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderSixStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OrderMessage> kStreamSix(StreamsBuilder builder) {
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
                .branch(isPlastic(), kStream -> kStream.to(topicsProperties.getOrdersPatternSixPlasticTopic(), producedWith))
                .defaultBranch(kStream -> kStream.to(topicsProperties.getOrdersPatternSixNoPlasticTopic(), producedWith))
                .onTopOf(maskedOrderStream.mapValues(StreamUtils::mapToOrderPattern))
                .print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("Order Pattern Stream"));

        // Third transformation and third sink
        KStream<String, OrderRewardMessage>  orderRewardStream = maskedOrderStream.filter(isLargeQuantity())
                .filterNot(isCheap())
                .map(mapToOrderRewardChangeKey());
        orderRewardStream.to(topicsProperties.getOrdersRewardSixTopic(), Produced.with(stringSerDe, orderRewardJsonSerDe));
        orderRewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("Order Reward Stream"));

        // Fourth sink
        // Generating new key
        KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(generateStorageKey());
        storageStream.to(topicsProperties.getOrdersStorageSixTopic(), Produced.with(stringSerDe, orderMessageJsonSerDe));
        storageStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Storage Order Stream"));

        // Stream for fraud
        KStream<String, Double> fraudStream = maskedOrderStream.filter((k, v) -> v.getOrderLocation().toUpperCase().startsWith("M"))
                .peek((k, v) -> this.reportFraud(v))
                .map((k, v) -> KeyValue.pair(v.getOrderLocation().toUpperCase().charAt(0) + "***", v.getPrice() * v.getQuantity()));
        fraudStream.to(topicsProperties.getOrdersFraudSixTopic(), Produced.with(stringSerDe, Serdes.Double()));
        fraudStream.print(Printed.<String, Double>toSysOut().withLabel("Fraud Order Stream"));

        return maskedOrderStream;
    }

    private void reportFraud(OrderMessage v) {
        log.info("Reporting fraud {}", v);
    }
}
