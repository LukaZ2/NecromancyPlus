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

public class AdditionalSoulInfo {

    public static void onTooltip(List<String> toolTip, ItemStack itemStack) {
        if(Module.ADDITIONAL_SOUL_INFO.getState() == 0) {
            return;
        }
        toolTip.add(" ");
        toolTip.add(EnumChatFormatting.GREEN + "[Additional Info]");
        for (int i = 0; i < SoulType.values().length; i++) {
            if(!Utils.clearColour(itemStack.getDisplayName()).equalsIgnoreCase(SoulType.values()[i].soulName)) {
                continue;
            }
            toolTip.add(EnumChatFormatting.GREEN + "Speed: " + SoulType.values()[i].speed);
            if(SoulType.values()[i].description != null) {
                toolTip.add(EnumChatFormatting.GREEN + "Description: ");
                toolTip.add(EnumChatFormatting.GREEN + SoulType.values()[i].description);
            }
            toolTip.add(" ");
            return;
        }
        toolTip.add(EnumChatFormatting.RED + "No information found.");
        toolTip.add(" ");
    }

}
