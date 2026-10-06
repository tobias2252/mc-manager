package dev.tobiasbieber.mcmanager.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

    // TODO make configurable later
    public static final DateTimeFormatter DEFAULT_DATE_TIME = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm")
            .withZone(ZoneId.systemDefault());

    public static String stripMinecraftFormatting(String text) {
        return text.replaceAll("(?i)§[0-9A-FK-OR]", "").replace("\n", " | ");
    }
}
