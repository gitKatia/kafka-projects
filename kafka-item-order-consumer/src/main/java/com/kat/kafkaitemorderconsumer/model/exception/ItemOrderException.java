package com.kat.kafkaitemorderconsumer.model.exception;

public class ItemOrderException extends RuntimeException {

    public ItemOrderException(String message) {
        super(message);
    }

    public ItemOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
