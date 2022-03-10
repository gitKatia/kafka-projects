package com.kat.kafkainventoryreducestream.broker.stream;

import com.kat.kafkainventoryreducestream.config.TopicsProperties;
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

@Configuration
@RequiredArgsConstructor
public class InventoryStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<InventoryMessage> inventoryJsonSerDe = new JsonSerde<>(InventoryMessage.class);
        Serde<Long> longSerDe = Serdes.Long();

        KStream<String, InventoryMessage> inventoryStream = builder.stream(topicsProperties.getInventoryTopic(),
                Consumed.with(stringSerDe, inventoryJsonSerDe));
        inventoryStream.print(Printed.<String,InventoryMessage>toSysOut().withLabel("Inventory Stream"));

        // For the same key = item we need to count the occurrences, i.e. total number of a specific item

        // transforming to a KStream<String, Long>
        KStream<String, Long> inventoryTotalStream = inventoryStream.mapValues((k, v) -> getQuantity(v))
                .groupByKey()
                // KGroupedStream is an intermediate representation of a stream in order to apply an aggregation
                // The initializer is applied once when the first record is processed
                // We need to specify how to materialize the state store
                .reduce(Long::sum, Materialized.with(stringSerDe, longSerDe) )
                // KTable should be transformed back into a KStream
                .toStream();
        inventoryTotalStream.to(topicsProperties.getInventoryTotalTopic(), Produced.with(stringSerDe, longSerDe));
        inventoryTotalStream.print(Printed.<String,Long>toSysOut().withLabel("Inventory Total Reduce Stream"));

        return inventoryStream;
    }

    private long getQuantity(InventoryMessage inventoryMessage) {
        return inventoryMessage.getType().equalsIgnoreCase(InventoryOperation.ADD.getOperation()) ?
                inventoryMessage.getQuantity(): -1 * inventoryMessage.getQuantity();
    }
}
