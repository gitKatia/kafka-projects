package com.kat.kafkaorderpaymentinnerjoinstream.broker.stream;

import com.kat.kafkaorderpaymentinnerjoinstream.broker.message.OnlineOrderPaymentMessage;
import com.kat.kafkaorderpaymentinnerjoinstream.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
import com.kat.ordersmodel.InventoryOperation;
import com.kat.ordersmodel.OnlineOrderMessage;
import com.kat.ordersmodel.OnlinePaymentMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class OrderPaymentStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, OnlineOrderMessage> kStreamOrderPayment(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<OnlineOrderMessage> onlineOrderJsonSerDe = new JsonSerde<>(OnlineOrderMessage.class);
        JsonSerde<OnlinePaymentMessage> onlinePaymentJsonSerDe = new JsonSerde<>(OnlinePaymentMessage.class);
        JsonSerde<OnlineOrderPaymentMessage> onlineOrderPaymentJsonSerDe = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        // Processing the incoming streams we want to join
        KStream<String, OnlineOrderMessage> onlineOrderStream = builder.stream(topicsProperties.getOnlineOrderTopic(),
                Consumed.with(stringSerDe, onlineOrderJsonSerDe, new OnlineOrderTimestampExtractor(), null));
        onlineOrderStream.print(Printed.<String, OnlineOrderMessage>toSysOut().withLabel("Online Order Stream"));
        KStream<String, OnlinePaymentMessage> onlinePaymentStream = builder.stream(topicsProperties.getOnlinePaymentTopic(),
                Consumed.with(stringSerDe, onlinePaymentJsonSerDe, new OnlinePaymentTimestampExtractor(), null));
        onlinePaymentStream.print(Printed.<String, OnlinePaymentMessage>toSysOut().withLabel("Online Payment Stream"));

        // join
        KStream<String, OnlineOrderPaymentMessage> onlineOrderPaymentStream = onlineOrderStream
                .join(onlinePaymentStream, this::joinOnlineOrderPayment, JoinWindows.of(Duration.ofHours(1)),
                        StreamJoined.with(stringSerDe, onlineOrderJsonSerDe, onlinePaymentJsonSerDe));
        onlineOrderPaymentStream.print(Printed.<String, OnlineOrderPaymentMessage>toSysOut().withLabel("Online Order Payment Stream"));
        onlineOrderPaymentStream.to(topicsProperties.getOnlineOrderPaymentTopic(), Produced.with(stringSerDe, onlineOrderPaymentJsonSerDe));

        return onlineOrderStream;
    }

    private OnlineOrderPaymentMessage joinOnlineOrderPayment(OnlineOrderMessage onlineOrderMessage, OnlinePaymentMessage onlineOrderPayment) {
        return OnlineOrderPaymentMessage.builder()
                .onlineOrderNumber(onlineOrderMessage.getOnlineOrderNumber())
                .orderDateTime(onlineOrderMessage.getOrderDateTime())
                .totalAmount(onlineOrderMessage.getTotalAmount())
                .username(onlineOrderMessage.getUsername())
                .paymentDateTime(onlineOrderPayment.getPaymentDateTime())
                .paymentMethod(onlineOrderPayment.getPaymentMethod())
                .paymentNumber(onlineOrderPayment.getPaymentNumber())
                .build();
    }
}
