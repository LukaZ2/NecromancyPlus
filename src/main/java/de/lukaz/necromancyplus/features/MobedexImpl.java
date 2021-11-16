package de.lukaz.necromancyplus.features;

import de.lukaz.necromancyplus.enums.Module;
import de.lukaz.necromancyplus.enums.SoulType;
import de.lukaz.necromancyplus.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class MobedexImpl {

    public static void onTooltip(List<String> toolTip, ItemStack itemStack) {
        if(Module.MOBEDEX_IMPLEMENTATION.getState() == 0) {
            return;
        }
        if(toolTip.size() < 4) {
            return;
        }
        if(!toolTip.get(5).contains("Click to remove!")) {
            return;
        }
        toolTip.add(" ");
        toolTip.add(EnumChatFormatting.GREEN + "[Mobedex]");
        toolTip.add(EnumChatFormatting.GREEN + "Made by _StarrySkies. (Last updated: November 16th, 2021)");
        for (int i = 0; i < SoulType.values().length; i++) {
            if(!Utils.clearColour(itemStack.getDisplayName()).equalsIgnoreCase(SoulType.values()[i].soulName)) {
                continue;
            }
            toolTip.add(EnumChatFormatting.GREEN + "Speed: " + SoulType.values()[i].speed);
            toolTip.add(EnumChatFormatting.GREEN + "Description: ");
            if(SoulType.values()[i].description != null) {
                toolTip.add(EnumChatFormatting.GREEN + SoulType.values()[i].description);
            } else {
                toolTip.add(EnumChatFormatting.GREEN + "No description.");
            }
            toolTip.add(" ");
            return;
        }
        toolTip.add(EnumChatFormatting.RED + "No information found.");
        toolTip.add(" ");
    }

}
