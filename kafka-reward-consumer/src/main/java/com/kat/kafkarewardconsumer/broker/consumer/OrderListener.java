package com.kat.kafkarewardconsumer.broker.consumer;

import com.kat.ordersmodel.OrderMessage;
import com.kat.ordersmodel.OrderReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderListener {

	@KafkaListener(topics = "${kafka-reward-consumer.orders-topic}")
	@SendTo("${kafka-reward-consumer.orders-reply-to-topic}")
	public OrderReplyMessage listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
		Headers headers = consumerRecord.headers();
		OrderMessage orderMessage = consumerRecord.value();

		log.info("Processing order {}, item {}.", orderMessage.getOrderNumber(), orderMessage.getItemName());
		log.info("Headers:");
		headers.forEach(h -> log.info("  key : {}, value : {}", h.key(), new String(h.value())));

		double bonusPercentage = Double.parseDouble(new String(headers.lastHeader("surpriseBonus").value()));
		double bonusAmount = (bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity();

		log.info("Surprise bonus is {}", bonusAmount);
		OrderReplyMessage replyMessage = new OrderReplyMessage();
		replyMessage.setReplyMessage(
				"Order " + orderMessage.getOrderNumber() + " item " + orderMessage.getItemName() + " processed.");
		return replyMessage;
	}
}
