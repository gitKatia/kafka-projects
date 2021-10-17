package com.kat.kafkajsonproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class User {

    @JsonProperty("user_id")
    private String userId;
    private String name;
    @JsonProperty("join_date")
    private LocalDate joinDate;
}
