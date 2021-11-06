package com.kat.kafkaflightrequestconsumer.model.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;

@Slf4j
public class GlobalErrorHandler implements ConsumerAwareErrorHandler {

	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
		log.warn("Global error handler for message {}", data.value());
	}
}
