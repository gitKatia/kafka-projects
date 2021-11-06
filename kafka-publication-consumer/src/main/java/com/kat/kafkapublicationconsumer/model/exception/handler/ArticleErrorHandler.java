package com.kat.kafkapublicationconsumer.model.exception.handler;

import com.kat.kafkapublicationconsumer.model.exception.PublicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ArticleErrorHandler implements ConsumerAwareListenerErrorHandler {

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		log.warn("Article error handler {} error, due to : {}", message.getPayload(), exception.getMessage());

		if (exception.getCause() instanceof PublicationException) {
			log.error("Article generated error. Rethrowing error.");
		}

		throw  exception;
	}

}
