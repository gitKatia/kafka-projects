package com.kat.kafkapersonproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {
    @JsonProperty("person_id")
    private String personId;
    private int age;
}
