package com.kat.kafkastreams.broker.stream;

import com.kat.kafkastreams.config.TopicsProperties;
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

@Configuration
@RequiredArgsConstructor
public class MaskOrderCreditCardStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OrderMessage> kStreamMaskOrderCreditCard(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<OrderMessage> orderMessageJsonSerDe = new JsonSerde<>(OrderMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder
                .stream(topicsProperties.getOrdersTopic(), Consumed.with(stringSerDe, orderMessageJsonSerDe))
                .mapValues(this::maskCreditCard);

        maskedOrderStream.to(topicsProperties.getMaskedOrdersTopic(), Produced.with(stringSerDe, orderMessageJsonSerDe));
        maskedOrderStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));

        return maskedOrderStream;
    }

    private OrderMessage maskCreditCard(OrderMessage orderMessage) {
        OrderMessage transformedMessage = orderMessage.copy();
        String maskedCreditCardNumber = orderMessage.getCreditCardNumber().replaceAll(".",
                "*");
        transformedMessage.setCreditCardNumber(maskedCreditCardNumber);

        return transformedMessage;
    }
}
