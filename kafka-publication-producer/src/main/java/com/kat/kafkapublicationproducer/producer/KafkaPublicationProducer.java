package com.kat.kafkapublicationproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.kafkapublicationproducer.conf.TopicsProperties;
import com.kat.kafkapublicationproducer.model.Article;
import com.kat.kafkapublicationproducer.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPublicationProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final TopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    public void sendMessage(Article article){
        log.info("About to publish article {}", article);
        try {
            String articleAsJsonString = objectMapper.writeValueAsString(article);
            kafkaTemplate.send(topicsProperties.getArticleTopic(), article.getArticleId(), articleAsJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while converting object. Message: "  + e.getMessage());
        }
    }

    public void sendMessage(Book book){
        log.info("About to publish book {}", book);
        try {
            String bookAsJsonString = objectMapper.writeValueAsString(book);
            kafkaTemplate.send(topicsProperties.getArticleTopic(), book.getBookId(), bookAsJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Exception while converting object. Message: "  + e.getMessage());
        }
    }
}
