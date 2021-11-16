package de.lukaz.necromancyplus.gui;

import de.lukaz.necromancyplus.NecromancyPlus;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.enums.MessageType;
import de.lukaz.necromancyplus.enums.Module;
import de.lukaz.necromancyplus.utils.Utils;
import de.lukaz.necromancyplus.utils.gui.CustomButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class SettingsGui extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();

        buttonList.add(new SettingsButton(0, (width/2)-100, (int) (height * 0.2), "Dropped Soul Info", "modules", "souldropdisplay", Module.DISPLAY_DROPPED_SOULS));
        buttonList.add(new SettingsButton(0, (width/2)-100, (int) (height * 0.3), "Display Mana cost in Menu", "modules", "displaymanacost", Module.VIEW_MANA_COST_IN_MENU));
        buttonList.add(new SettingsButton(0, (width/2)-100, (int)(height*0.4), "Doubleclick to remove soul", "modules", "confirmremove", Module.CONFIRM_REMOVE));
        buttonList.add(new SettingsButton(0, (width/2)-100, (int)(height*0.5), "Display additional soul info", "modules", "additionalsoulinfo", Module.ADDITIONAL_SOUL_INFO));
        buttonList.add(new CustomButton(0, (width/2)-100, (int)(height*0.6), "Mana optimizer"));
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        this.drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        fontRendererObj.drawString("[Necromancy+] Settings menu", (width/2)-70, (int)(height*0.1), 0xffffff);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) {
        if(p_actionPerformed_1_ instanceof CustomButton) {
            ((CustomButton)p_actionPerformed_1_).performAction();
            if(p_actionPerformed_1_ instanceof SettingsButton) {
                if (((SettingsButton) p_actionPerformed_1_).module == Module.DISPLAY_DROPPED_SOULS) {
                    if (((SettingsButton) p_actionPerformed_1_).value != 1) {
                        return;
                    }
                    ChatHandler.sendMessage("WARNING: Please note that this module does not work in dungeons (CURRENTLY). Why? See https://github.com/LukaZ2/NecromancyPlus", MessageType.WARNING);
                    return;
                }
            }
            if(p_actionPerformed_1_.displayString.equals("Mana optimizer")) {
                NecromancyPlus.guiScreenToOpen = new ManaOptimizerGui(Minecraft.getMinecraft().thePlayer.inventory.mainInventory[8].getDisplayName().contains("SkyBlock Menu"), Utils.hasValidApi);
                return;
            }
        }
    }
}
