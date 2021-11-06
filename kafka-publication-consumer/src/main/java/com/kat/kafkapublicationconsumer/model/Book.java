package com.kat.kafkapublicationconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @JsonProperty("book_id")
    private String bookId;
    private String title;
    private String author;
    private double price;
    @JsonProperty("added_on")
    private LocalDate addedOn;
}
