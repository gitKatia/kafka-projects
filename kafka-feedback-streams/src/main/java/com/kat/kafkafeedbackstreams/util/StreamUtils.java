package com.kat.kafkafeedbackstreams.util;

import com.kat.ordersmodel.FeedbackMessage;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class StreamUtils {
    private static final Set<String> GOOD_WORDS = new HashSet<>(Arrays.asList("happy", "good", "helpful"));

    public static ValueMapper<FeedbackMessage, Iterable<String>> goodWordsMapper() {
        return feedbackMessage -> Arrays
                .asList(feedbackMessage.getFeedback().replaceAll("[^a-zA-Z ]", "")
                        .toLowerCase().split("\\s+"))
                .stream()
                .filter(word -> GOOD_WORDS.contains(word))
                .distinct()
                .collect(toList());
    }
}
