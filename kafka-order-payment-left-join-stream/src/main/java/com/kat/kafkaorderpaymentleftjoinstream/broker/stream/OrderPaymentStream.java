package com.kat.kafkaorderpaymentleftjoinstream.broker.stream;

import com.kat.kafkaorderpaymentleftjoinstream.config.TopicsProperties;
import com.kat.ordersmodel.OnlineOrderMessage;
import com.kat.ordersmodel.OnlineOrderPaymentMessage;
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
import java.util.Optional;

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
                // in case of outer join I would use outerJoin method
                .leftJoin(onlinePaymentStream, this::joinOnlineOrderPayment, JoinWindows.of(Duration.ofHours(1)),
                        StreamJoined.with(stringSerDe, onlineOrderJsonSerDe, onlinePaymentJsonSerDe));
        onlineOrderPaymentStream.print(Printed.<String, OnlineOrderPaymentMessage>toSysOut().withLabel("Online Order Payment Stream"));
        onlineOrderPaymentStream.to(topicsProperties.getOnlineOrderPaymentTopic(), Produced.with(stringSerDe, onlineOrderPaymentJsonSerDe));

        return onlineOrderStream;
    }

    private OnlineOrderPaymentMessage joinOnlineOrderPayment(OnlineOrderMessage onlineOrderMessage, OnlinePaymentMessage onlinePaymentMessage) {
        OnlineOrderPaymentMessage.OnlineOrderPaymentMessageBuilder builder = OnlineOrderPaymentMessage.builder()
                .onlineOrderNumber(onlineOrderMessage.getOnlineOrderNumber())
                .orderDateTime(onlineOrderMessage.getOrderDateTime())
                .totalAmount(onlineOrderMessage.getTotalAmount())
                .username(onlineOrderMessage.getUsername());
        // Being a left join onlinePaymentMessage with the same key can be null
        Optional.ofNullable(onlinePaymentMessage).ifPresent(message ->
            builder.paymentDateTime(message.getPaymentDateTime())
                    .paymentMethod(message.getPaymentMethod())
                    .paymentNumber(message.getPaymentNumber()));
        // In case of an outer join both onlineOrderMessage and onlinePaymentMessage could be null
        return builder.build();
    }
}
