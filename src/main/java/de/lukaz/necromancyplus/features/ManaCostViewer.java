package de.lukaz.necromancyplus.features;

import de.lukaz.necromancyplus.utils.Module;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ManaCostViewer {

    @SubscribeEvent
    public void itemTooltipEvent(ItemTooltipEvent event) {

        if(Module.VIEW_MANA_COST_IN_MENU.getState() == 0) {
            return;
        }

        if(event.toolTip == null) {
            return;
        }
        if(event.entityPlayer.inventory.getDisplayName() == null || event.entityPlayer.inventory.getDisplayName().toString().isEmpty()) {
            return;
        }
        if(event.toolTip.size() < 4) {
            return;
        }
        if(!event.toolTip.get(event.toolTip.size()-3).contains("Click to remove!")) {
            return;
        }
        String[] healthRaw = event.toolTip.get(1).split(":");
        String[] damageRaw = event.toolTip.get(2).split(":");

        int health = Integer.parseInt(healthRaw[1].replace(",", "").substring(3));
        int damage = Integer.parseInt(damageRaw[1].replace(",", "").substring(3));

        int mana_Cost = (health/100000)+(damage/50);
        int mana_maxReduction = mana_Cost;
        mana_maxReduction = (int) (mana_maxReduction*0.5); //Ultimate wise 5
        mana_maxReduction = (int) (mana_maxReduction*0.8); //Level 100 Sheep pet
        mana_maxReduction = (int) (mana_maxReduction*0.67); //Wise armor swap

        event.toolTip.add(EnumChatFormatting.GRAY + "This soul costs " + EnumChatFormatting.DARK_AQUA + mana_Cost + " Mana " + EnumChatFormatting.GRAY + "to spawn.");
        event.toolTip.add(EnumChatFormatting.GRAY + "You can reduce the mana cost to " + EnumChatFormatting.AQUA + mana_maxReduction + " Mana" + EnumChatFormatting.GRAY + ".");

    }
}
