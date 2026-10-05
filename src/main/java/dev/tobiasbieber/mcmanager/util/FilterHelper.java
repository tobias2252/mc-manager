package dev.tobiasbieber.mcmanager.util;

public class FilterHelper {

    public static boolean matchesFilter(String filter, String text) {
        String convFilter = filter.toLowerCase().trim().replace(" ", "");
        String convText = text.toLowerCase().trim().replace(" ", "");
        return convText.contains(convFilter);
    }
}
