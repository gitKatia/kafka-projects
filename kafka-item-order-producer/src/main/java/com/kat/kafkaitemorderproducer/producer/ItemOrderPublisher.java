package com.kat.kafkaitemorderproducer.producer;

import com.kat.kafkaitemorderproducer.model.ItemOrder;

public interface ItemOrderPublisher {
    void sendMessage(ItemOrder itemOrder);
}
