package com.kat.ordersmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMessage {
    private String feedback;
    private LocalDateTime feedbackDateTime;
    private String location;
    private int rating;

}
