package com.kat.feedbackdetailedratingstream.broker.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRatingStoreValue {

	private Map<Integer, Long> ratingMap = new TreeMap<>();
}
