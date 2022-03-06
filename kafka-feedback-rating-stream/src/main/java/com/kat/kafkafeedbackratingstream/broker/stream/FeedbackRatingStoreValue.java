package com.kat.kafkafeedbackratingstream.broker.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRatingStoreValue {

	private long ratingsCount;
	private long ratingsSum;
}
