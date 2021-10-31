package com.kat.kafkaitemorderconsumer.model.exception.handler;

import com.kat.kafkaitemorderconsumer.model.exception.ItemOrderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ItemOrderErrorHandler implements ConsumerAwareListenerErrorHandler {

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		log.warn("ItemOrder error {}, due to : {}", message.getPayload(), exception.getMessage());

		if (exception.getCause() instanceof ItemOrderException) {
			return null;
		}

		throw  exception;
	}

}
