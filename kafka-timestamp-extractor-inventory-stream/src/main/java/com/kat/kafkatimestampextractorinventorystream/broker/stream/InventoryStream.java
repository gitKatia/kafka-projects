package com.kat.kafkatimestampextractorinventorystream.broker.stream;

import com.kat.kafkatimestampextractorinventorystream.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
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
public class InventoryStream {

    private final TopicsProperties topicsProperties;

    @Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        Serde<String> stringSerDe = Serdes.String();
        JsonSerde<InventoryMessage> inventoryJsonSerDe = new JsonSerde<>(InventoryMessage.class);
        InventoryTimestampExtractor inventoryTimestampExtractor = new InventoryTimestampExtractor();

        KStream<String, InventoryMessage> inventoryStream = builder.stream(topicsProperties.getInventoryTopic(),
                Consumed.with(stringSerDe, inventoryJsonSerDe, inventoryTimestampExtractor, null));
        inventoryStream.print(Printed.<String,InventoryMessage>toSysOut().withLabel("Inventory Stream"));

        inventoryStream.to(topicsProperties.getInventoryTimestampTopic(), Produced.with(stringSerDe, inventoryJsonSerDe));
        inventoryStream.print(Printed.<String,InventoryMessage>toSysOut().withLabel("Inventory Stream with timestamp"));

        return inventoryStream;
    }
}
