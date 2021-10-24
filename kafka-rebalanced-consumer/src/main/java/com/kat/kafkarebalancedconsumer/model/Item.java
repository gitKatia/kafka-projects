package com.kat.kafkarebalancedconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    @JsonProperty("item_id")
    private String itemId;
    @JsonProperty("item_name")
    private String itemName;
    @JsonProperty("created_on")
    private LocalDate createdOn;
}
