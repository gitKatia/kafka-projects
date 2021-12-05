package com.kat.kafkapublicationproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Article {
    @JsonProperty("article_id")
    private String articleId;
    private String title;
    private String author;
    private double price;
    @JsonProperty("added_on")
    private LocalDate addedOn;
}
