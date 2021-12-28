package com.kat.kafkastreamsone.util;

import com.kat.kafkastreamsone.broker.message.OrderPatternMessage;
import com.kat.kafkastreamsone.broker.message.OrderRewardMessage;
import com.kat.ordersmodel.OrderMessage;
import org.apache.kafka.streams.kstream.Predicate;

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
}
