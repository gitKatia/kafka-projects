package com.kat.kafkaitemorderproducer.service;

import com.kat.kafkaitemorderproducer.model.ItemOrder;
import com.kat.kafkaitemorderproducer.producer.ItemOrderPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemOrderServiceImpl implements ItemOrderService {
    private final ItemOrderPublisher itemOrderPublisher;

    private List<ItemOrder> getOrders() {
        ItemOrder itemOrder1 = ItemOrder.builder()
                .orderId(UUID.randomUUID().toString())
                .itemName("Green blanket")
                .quantity(Math.abs(new Random().nextInt(10)))
                .orderDate(LocalDate.now())
                .build();
        ItemOrder itemOrder2 = ItemOrder.builder()
                .orderId(UUID.randomUUID().toString())
                .itemName("Blue blanket")
                .quantity(Math.abs(new Random().nextInt(15)))
                .orderDate(LocalDate.now())
                .build();
        ItemOrder itemOrder3 = ItemOrder.builder()
                .orderId(UUID.randomUUID().toString())
                .itemName("Pink blanket")
                .quantity(-1)
                .orderDate(LocalDate.now())
                .build();
        return Arrays.asList(itemOrder1, itemOrder2, itemOrder3);
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void sendItemOrders() {
        List<ItemOrder> itemOrders = getOrders();
        itemOrders.forEach(itemOrder -> itemOrderPublisher.sendMessage(itemOrder));
    }
}
