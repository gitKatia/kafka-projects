package com.kat.kafkapremiumofferstream.util;

import com.kat.ordersmodel.PremiumUserMessage;

import java.util.Arrays;
import java.util.List;

public class StreamsUtil {

    private static final List<String> LEVELS = Arrays.asList("gold", "diamond");

    public static boolean isPremiumUser(PremiumUserMessage premiumUserMessage) {
        return  LEVELS.contains(premiumUserMessage.getLevel().toLowerCase());
    }
}
