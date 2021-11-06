package com.kat.kafkapublicationconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkapublicationconsumer.model.Article;
import com.kat.kafkapublicationconsumer.model.Book;
import com.kat.kafkapublicationconsumer.model.exception.PublicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPublicationConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka-publication-consumer.article-topic}", groupId = "article-consumer-group",
            errorHandler = "articleErrorHandler")
    public void consumeArticle(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
        Article article = objectMapper.readValue(message, Article.class);
        if(article.getPrice() <= 0.0) {
            throw new PublicationException(format("Article with id %s has a non-positive price %f", article.getArticleId(),
                    article.getPrice()));
        }
    }

    @KafkaListener(topics = "${kafka-publication-consumer.book-topic}", groupId = "book-consumer-group")
    public void consumeBook(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        log.info("Received json message {} on topic {}", message, topic);
        Book book = objectMapper.readValue(message, Book.class);
        if(book.getPrice() <= 0.0) {
            throw new PublicationException(format("Book with id %s has a non-positive price %f", book.getBookId(),
                    book.getPrice()));
        }
    }
}
