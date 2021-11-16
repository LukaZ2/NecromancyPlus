package de.lukaz.necromancyplus.gui;

import de.lukaz.necromancyplus.handlers.SettingsHandler;
import de.lukaz.necromancyplus.enums.Module;
import de.lukaz.necromancyplus.utils.gui.CustomButton;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class SettingsButton extends CustomButton {

    public int options_count;
    public List<String> options;
    public String settings_category, settings_key, name;
    public Module module;

    public int value;

    public SettingsButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_, String settings_category, String settings_key, Module module) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);

        options = new ArrayList();
        options.add(EnumChatFormatting.RED + "Off");
        options.add(EnumChatFormatting.GREEN + "On");
        options_count = options.size();

        this.settings_category = settings_category;
        this.settings_key = settings_key;
        this.module = module;

        value = SettingsHandler.getIntValue(settings_category, settings_key);

        name = p_i1020_4_;

        refreshButton();
    }
    public SettingsButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_, List<String> options, String settings_category, String settings_key, Module module) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
        this.options = options;
        this.options_count = options.size();

        this.settings_category = settings_category;
        this.settings_key = settings_key;
        this.module = module;

        value = SettingsHandler.getIntValue(settings_category, settings_key);

        name = p_i1020_4_;

        refreshButton();
    }

    public void refreshButton() {
        displayString = name + ": " + options.get(value);
    }

    public void toggle() {
        if(value+1 >= options.size()) {
            value = 0;
            refreshButton();
            module.setState(value);
            SettingsHandler.setIntValue(settings_category, settings_key, value);
            return;
        }
        value++;
        refreshButton();
        module.setState(value);
        SettingsHandler.setIntValue(settings_category, settings_key, value);
    }

    @Override
    public void performAction() {
        toggle();
    }
}
