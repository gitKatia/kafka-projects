package com.kat.kafkastreamstwo.util;

import com.kat.kafkastreamstwo.broker.message.OrderPatternMessage;
import com.kat.kafkastreamstwo.broker.message.OrderRewardMessage;
import com.kat.ordersmodel.OrderMessage;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.util.StringUtils;

import java.util.Base64;

public class StreamUtils {

    public static OrderMessage maskCreditCard(OrderMessage orderMessage) {
        OrderMessage transformedMessage = orderMessage.copy();
        String maskedCreditCardNumber = orderMessage.getCreditCardNumber().replaceAll(".",
                "*");
        transformedMessage.setCreditCardNumber(maskedCreditCardNumber);

        return transformedMessage;
    }

    public static OrderPatternMessage mapToOrderPattern(OrderMessage orderMessage) {

        OrderPatternMessage orderPatternMessage = new OrderPatternMessage();

        orderPatternMessage.setItemName(orderMessage.getItemName());
        orderPatternMessage.setOrderDateTime(orderMessage.getOrderDateTime());
        orderPatternMessage.setOrderLocation(orderMessage.getOrderLocation());
        orderPatternMessage.setOrderNumber(orderMessage.getOrderNumber());

        double totalItemAmount = orderMessage.getPrice() * orderMessage.getQuantity();
        orderPatternMessage.setTotalItemAmount(totalItemAmount);

        return orderPatternMessage;
    }

    public static OrderRewardMessage mapToOrderReward(OrderMessage orderMessage) {

        OrderRewardMessage orderRewardMessage = new OrderRewardMessage();

        orderRewardMessage.setItemName(orderMessage.getItemName());
        orderRewardMessage.setOrderDateTime(orderMessage.getOrderDateTime());
        orderRewardMessage.setOrderLocation(orderMessage.getOrderLocation());
        orderRewardMessage.setOrderNumber(orderMessage.getOrderNumber());
        orderRewardMessage.setPrice(orderMessage.getPrice());
        orderRewardMessage.setQuantity(orderMessage.getQuantity());

        return orderRewardMessage;
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, value) -> value.getQuantity() > 200;
    }

    public static Predicate<String, OrderPatternMessage> isPlastic() {
        return (key, value) -> StringUtils.startsWithIgnoreCase(value.getItemName(), "Plastic");
    }

    public static Predicate<String, OrderMessage> isCheap() {
        return (key, value) -> value.getPrice() < 100;
    }

    // takes kayType, valueType and returns MappedValueType
    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (key, value) -> Base64.getEncoder().encodeToString(value.getOrderNumber().getBytes());
    }
}
