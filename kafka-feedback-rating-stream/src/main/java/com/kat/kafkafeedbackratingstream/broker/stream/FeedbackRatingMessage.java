package com.kat.kafkafeedbackratingstream.broker.stream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackRatingMessage {

	private String location;
	private double averageRating;
}
