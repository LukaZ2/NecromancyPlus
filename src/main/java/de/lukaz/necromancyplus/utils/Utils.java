package de.lukaz.necromancyplus.utils;

public class Utils {

    public static String clearColour(String text) {
        return text.replaceAll("(?i)\\u00A7.", "");
    }

}
