package de.lukaz.necromancyplus.gui;

import de.lukaz.necromancyplus.features.manaoptimizer.ManaOptimizer;
import de.lukaz.necromancyplus.features.manaoptimizer.OptimizerResultText;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.handlers.SettingsHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import de.lukaz.necromancyplus.utils.gui.CustomButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ManaOptimizerGui extends GuiScreen {

    public boolean scanning = false;
    public boolean inSkyBlock;
    public boolean hasApi;

    public ManaOptimizerGui(boolean inSkyBlock, boolean hasApi) {
        this.inSkyBlock = inSkyBlock;
        this.hasApi = hasApi;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        drawDefaultBackground();
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);

        fontRendererObj.drawString("[Necromancy+] Mana optimizer",(width/2)-70, (int)(height*0.1D), 0xffffff);
        fontRendererObj.drawString("This module helps you to optimize your intelligence.", (width/2)-130, (int)(height*0.15), 0xffffff);
        fontRendererObj.drawString("[WARNING] If you find any optimization option that is not covered in the mod,", (width/2)-200, (int)(height*0.2), 0xfcf003);
        fontRendererObj.drawString("feel free to contact Lukaz#1741 on discord.", (width/2)-130, (int)(height*0.25), 0xfcf003);
        CustomButton optimizeButton = new CustomButton(0, (width / 2) - 100, (int) (height * 0.35), "Start optimization");
        if(scanning) {
            optimizeButton.displayString = "Optimizing...";
            optimizeButton.enabled = false;
        }
        if(optimizeButton.displayString.equals("Start optimization")) {
            if (!hasApi) {
                optimizeButton.displayString = "Please type /api new";
                optimizeButton.enabled = false;
            }
        }
        if(optimizeButton.displayString.equals("Start optimization")) {
            if (!inSkyBlock) {
                optimizeButton.displayString = "Please join Skyblock";
                optimizeButton.enabled = false;
            }
        }
        buttonList.add(optimizeButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) {
        if(p_actionPerformed_1_.displayString.equals("Start optimization")) {
            if(scanning) {
                return;
            }
            scanning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<OptimizerResultText> result = ManaOptimizer.optimize();
                    ChatHandler.sendMessage("Result for your mana optimization:", MessageType.INFO);
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    ChatHandler.sendMessage(" ", MessageType.INFO);
                    int totalManaOptimized = 0;
                    for (int i = 0; i < result.size(); i++) {
                        if(result.get(i) == null) {
                            continue;
                        }
                        if (!result.get(i).success) {
                            ChatHandler.sendMessage(result.get(i).text + EnumChatFormatting.YELLOW + " (Missing on " + result.get(i).optimizedMana + " mana)", MessageType.WARNING);
                        }
                    }
                    if(result.size() == 0) {
                        ChatHandler.sendMessage("Well, nothing found to be optimized :c", MessageType.INFO);
                    }
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    ChatHandler.sendMessage(" ", MessageType.INFO);
                }
            }).start();
        }

        }
    }
