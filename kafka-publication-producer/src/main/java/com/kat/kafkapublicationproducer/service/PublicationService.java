package com.kat.kafkapublicationproducer.service;

import com.kat.kafkapublicationproducer.model.Article;
import com.kat.kafkapublicationproducer.model.Book;
import com.kat.kafkapublicationproducer.producer.KafkaPublicationProducer;
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
public class PublicationService {

    private final KafkaPublicationProducer kafkaPublicationProducer;

    private List<Article> getArticles() {
        double price1 = 1.0 + new Random().nextDouble();
        Article article1 = Article.builder()
                .articleId(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .title(UUID.randomUUID().toString())
                .addedOn(LocalDate.now())
                .price(price1)
                .build();
        Article article2 = Article.builder()
                .articleId(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .title(UUID.randomUUID().toString())
                .addedOn(LocalDate.now())
                .price(-2.0)
                .build();
        return Arrays.asList(article1, article2);
    }

    @Scheduled(fixedRate = 1000)
    public void publishArticles() {
        getArticles().forEach(kafkaPublicationProducer::sendMessage);
    }

    private List<Book> getBooks() {
        double price1 = 2.0 + new Random().nextDouble();
        Book book1 = Book.builder()
                .bookId(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .title(UUID.randomUUID().toString())
                .addedOn(LocalDate.now())
                .price(price1)
                .build();
        Book book2 = Book.builder()
                .bookId(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .title(UUID.randomUUID().toString())
                .addedOn(LocalDate.now())
                .price(-3.0)
                .build();
        return Arrays.asList(book1, book2);
    }

    @Scheduled(fixedRate = 2000)
    public void publishBook() {
        getBooks().forEach(kafkaPublicationProducer::sendMessage);
    }
}
