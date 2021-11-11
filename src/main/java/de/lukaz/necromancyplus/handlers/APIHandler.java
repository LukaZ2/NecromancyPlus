package de.lukaz.necromancyplus.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.lukaz.necromancyplus.utils.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {

    public static final String SKYCRYPT_API_URL = "https://sky.shiiyu.moe/api/v2/";
    public static final String HYPIXEL_API_URL = "https://api.hypixel.net/";

    public static JsonObject get(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        if(httpURLConnection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonObject.class);
        }
        ChatHandler.sendMessage("An error occoured! Response code " + httpURLConnection.getResponseCode(), MessageType.ERROR);
        return null;
    }

    public static JsonObject getProfile(String playerName) {
        try {
            return get(new URL(SKYCRYPT_API_URL + "profile/" + playerName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonArray getTalismans(String playerName) {
        try {
            return get(new URL(SKYCRYPT_API_URL + "talismans/" + playerName)).get("profiles").getAsJsonObject().get(getCurrentProfile(playerName)).getAsJsonObject().get("talismans").getAsJsonArray();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getUUID(String username) {
        JsonObject uuidResponse = null;
        try {
            uuidResponse = get(new URL("https://api.mojang.com/users/profiles/minecraft/" + username));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuidResponse.get("id").getAsString();
    }

    public static String getCurrentProfile(String playerName) throws IOException {
        String uuid = getUUID(playerName);
        String currentProfile = "";
        long latestSave = 0;
        JsonArray profiles = get(new URL(HYPIXEL_API_URL + "skyblock/profiles?uuid=" + uuid + "&key=" + SettingsHandler.getStringValue("strings", "api"))).get("profiles").getAsJsonArray();
        for (int i = 0; i < profiles.size(); i++) {
            JsonObject profile = profiles.get(i).getAsJsonObject();
            long lastSave = 1;
            if (profile.get("members").getAsJsonObject().get(uuid.toString()).getAsJsonObject().has("last_save")) {
                lastSave = profile.get("members").getAsJsonObject().get(uuid.toString()).getAsJsonObject().get("last_save").getAsLong();
            }
            if (lastSave > latestSave) {
                currentProfile = profile.get("profile_id").getAsString();
                latestSave = lastSave;
            }
        }
        return currentProfile;
        }

    }
