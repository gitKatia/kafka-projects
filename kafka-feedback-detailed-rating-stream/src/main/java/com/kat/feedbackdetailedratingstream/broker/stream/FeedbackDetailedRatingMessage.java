package com.kat.feedbackdetailedratingstream.broker.stream;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
public class FeedbackDetailedRatingMessage {

	private String location;
	private double averageRating;
	private Map<Integer, Long> ratingMap;
}
