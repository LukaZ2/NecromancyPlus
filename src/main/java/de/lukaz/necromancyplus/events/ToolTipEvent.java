package de.lukaz.necromancyplus.events;

import de.lukaz.necromancyplus.features.MobedexImpl;
import de.lukaz.necromancyplus.features.ManaCostViewer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolTipEvent {

     @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
         ManaCostViewer.onTooltip(event.toolTip);
         MobedexImpl.onTooltip(event.toolTip, event.itemStack);
     }

}
