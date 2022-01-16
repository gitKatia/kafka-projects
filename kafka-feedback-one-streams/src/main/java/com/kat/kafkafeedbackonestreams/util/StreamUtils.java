package com.kat.kafkafeedbackonestreams.util;

import com.kat.ordersmodel.FeedbackMessage;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class StreamUtils {
    private static final Set<String> GOOD_WORDS = new HashSet<>(Arrays.asList("happy", "good", "helpful"));

    public static KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> goodWordsKeyValueMapper() {
        return (k, v) -> Arrays
                .asList(v.getFeedback().replaceAll("[^a-zA-Z ]", "")
                        .toLowerCase().split("\\s+"))
                .stream()
                .filter(word -> GOOD_WORDS.contains(word))
                .distinct()
                .map(goodWord -> KeyValue.pair(v.getLocation(), goodWord))
                .collect(toList());
    }
}
