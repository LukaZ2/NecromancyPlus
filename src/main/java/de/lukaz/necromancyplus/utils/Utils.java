package de.lukaz.necromancyplus.utils;

import com.google.gson.JsonObject;
import de.lukaz.necromancyplus.handlers.APIHandler;
import de.lukaz.necromancyplus.handlers.SettingsHandler;

public class Utils {

    public static String clearColour(String text) {
        return text.replaceAll("(?i)\\u00A7.", "");
    }
    public static boolean hasValidApi;

    public static int romanNumberToInt(String romanNumber) {
        if(romanNumber.equals("I")) return 1;
        if(romanNumber.equals("II")) return 2;
        if(romanNumber.equals("III")) return 3;
        if(romanNumber.equals("IV")) return 4;
        if(romanNumber.equals("V")) return 5;
        if(romanNumber.equals("VI")) return 6;
        if(romanNumber.equals("VII")) return 7;
        if(romanNumber.equals("VIII")) return 8;
        if(romanNumber.equals("IX")) return 9;
        if(romanNumber.equals("X")) return 10;
        return Integer.parseInt(romanNumber);
    }

    public static void refreshApi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!SettingsHandler.hasValue("strings", "api")) {
                    hasValidApi = false;
                    return;
                }
                String apiKey = SettingsHandler.getStringValue("strings", "api");
                JsonObject response = APIHandler.getApiKey(apiKey);
                if(response == null) {
                    hasValidApi = false;
                    return;
                }
                hasValidApi = response.get("success").getAsBoolean();
            }
        }).start();

    }


}
