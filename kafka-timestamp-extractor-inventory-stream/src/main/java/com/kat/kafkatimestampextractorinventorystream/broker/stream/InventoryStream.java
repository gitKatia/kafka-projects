package com.kat.kafkatimestampextractorinventorystream.broker.stream;

import com.kat.kafkatimestampextractorinventorystream.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
import com.kat.ordersmodel.InventoryOperation;
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
public class InventoryStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        // Serializers and deserializers for first processor
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<InventoryMessage> inventoryJsonSerDe = new JsonSerde<>(InventoryMessage.class);

        // Serializers and deserializers for second processor
        Serde<Long> longSerDe = Serdes.Long();
        // window size is 1h
        Duration windowLength = Duration.ofHours(1);
        // Maybe because timestamp is a string?
        Serde<Windowed<String>> windowSerDe = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

        InventoryTimestampExtractor inventoryTimestampExtractor = new InventoryTimestampExtractor();

        // Consuming the stream - First processor
        KStream<String, InventoryMessage> inventoryStream = builder.stream(topicsProperties.getInventoryTopic(),
                Consumed.with(stringSerDe, inventoryJsonSerDe, inventoryTimestampExtractor, null));
        inventoryStream.print(Printed.<String,InventoryMessage>toSysOut().withLabel("Inventory Stream"));

        KStream<Windowed<String>, Long> windowedStream = inventoryStream
                .mapValues((k, v) -> getQuantity(v))
                .groupByKey()
                .windowedBy(TimeWindows.of(windowLength))
                .reduce(Long::sum, Materialized.with(stringSerDe, longSerDe)).toStream();
        windowedStream.print(Printed.<Windowed<String>, Long>toSysOut().withLabel("Inventory Tumbling Window Stream"));
        windowedStream.to(topicsProperties.getInventoryTimestampTopic(), Produced.with(windowSerDe, longSerDe));

        return inventoryStream;
    }

    private long getQuantity(InventoryMessage inventoryMessage) {
        return inventoryMessage.getType().equalsIgnoreCase(InventoryOperation.ADD.getOperation()) ?
                inventoryMessage.getQuantity(): -1 * inventoryMessage.getQuantity();
    }
}
