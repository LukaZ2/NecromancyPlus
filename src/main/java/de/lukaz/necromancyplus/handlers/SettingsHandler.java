package de.lukaz.necromancyplus.handlers;

import de.lukaz.necromancyplus.utils.Module;
import net.minecraftforge.common.config.Configuration;
import scala.DeprecatedConsole;

import java.io.File;

public class SettingsHandler {

    public static final String fileName = "config/NecromancyPlus.cfg";

    public static void setIntValue(String category, String key, int value) {
        Configuration config = new Configuration(new File(fileName));
        try {
            config.load();
            config.getCategory(category).get(key).set(value);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
    }
    public static void setStringValue(String category, String key, String value) {
        Configuration config = new Configuration(new File(fileName));
        try {
            config.load();
            config.getCategory(category).get(key).set(value);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
    }

    public static Integer getIntValue(String category, String key) {
        Configuration config = new Configuration(new File(fileName));
        try {
            config.load();
            return config.get(category, key, 0).getInt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
        return 0;
    }
    public static String getStringValue(String category, String key) {
        Configuration config = new Configuration(new File(fileName));
        try {
            config.load();
            return config.get(category, key, 0).getString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
        return "";
    }

    public static boolean hasValue(String category, String key) {
        Configuration config = new Configuration(new File(fileName));

        try {
            config.load();
            if(!config.hasCategory(category)) {
                return false;
            }
            return config.getCategory(category).containsKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
        return false;
    }

    public static int initValue(String category, String key, int defaultValue) {
        Configuration config = new Configuration(new File(fileName));
        try {
            config.load();
            if(config.hasCategory(category)) {
                if(!hasValue(category, key)) {
                    config.get(category, key, defaultValue);
                    return defaultValue;
                }
                return config.get(category, key, 0).getInt();
            }
            config.get(category, key, defaultValue);
            return defaultValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            config.save();
        }
        return 0;
    }

    public static void init() {
        Module.DISPLAY_DROPPED_SOULS.setState(initValue("modules", "souldropdisplay", 0));
        Module.VIEW_MANA_COST_IN_MENU.setState(initValue("modules", "displaymanacost", 1));
        Module.CONFIRM_REMOVE.setState(initValue("modules", "confirmremove", 1));
    }
}
