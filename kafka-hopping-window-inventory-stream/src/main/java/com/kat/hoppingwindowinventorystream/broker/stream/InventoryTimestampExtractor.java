package com.kat.hoppingwindowinventorystream.broker.stream;

import com.kat.ordersmodel.InventoryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InventoryTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
        InventoryMessage inventoryMessage = (InventoryMessage) consumerRecord.value();
        LocalDateTime transactionTime = inventoryMessage.getTransactionTime();
        return transactionTime != null ? toEpochTimestamp(transactionTime) : consumerRecord.timestamp();
    }

    private long toEpochTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
