package com.kat.kafkajsonconsumer.model;

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
public class User {

    @JsonProperty("user_id")
    private String userId;
    private String name;
    @JsonProperty("join_date")
    private LocalDate joinDate;
}
