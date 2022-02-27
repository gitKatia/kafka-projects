package com.kat.kafkafeedbackthreestreams.util;

import com.kat.ordersmodel.FeedbackMessage;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class StreamUtils {

    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList("angry", "sad", "bad"));
    private static final Set<String> GOOD_WORDS = new HashSet<>(Arrays.asList("happy", "good", "helpful"));

    public static Predicate<String, String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    public static Predicate<String, String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    public static KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> words() {
        return (key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct()
                .map(word -> KeyValue.pair(value.getLocation(), word))
                .collect(toList());
    }
}
