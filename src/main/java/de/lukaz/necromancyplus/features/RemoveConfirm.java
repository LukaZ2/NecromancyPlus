package de.lukaz.necromancyplus.features;

import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import de.lukaz.necromancyplus.utils.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.util.List;

public class RemoveConfirm {

    public static final int EXPIRATION_DELAY = 2000;

    public Slot confirmedSlot;
    public long expirationTime;

    @SubscribeEvent
    public void onGuiClick(GuiScreenEvent.MouseInputEvent.Pre event) {

        if(Mouse.getEventButton() != 0 && Mouse.getEventButton() != 1 && Mouse.getEventButton() != 2) {
            return;
        }
        if(!Mouse.getEventButtonState()) {
            return;
        }
        if(Module.CONFIRM_REMOVE.getState() == 0) {
            return;
        }
        if(!(event.gui instanceof GuiChest)) {
            return;
        }
        Container container = ((GuiChest)event.gui).inventorySlots;
        if(!(container instanceof ContainerChest)) {
            return;
        }
        GuiChest guiChest = (GuiChest) event.gui;
        if(!((ContainerChest)container).getLowerChestInventory().getDisplayName().getUnformattedText().equals("Soul Menu")) {
            return;
        }
        Slot slot = guiChest.getSlotUnderMouse();
        if(!slot.getHasStack()) {
            return;
        }
        ItemStack item = slot.getStack();
        if(!item.hasTagCompound()) {
            return;
        }

        List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        boolean soul = false;
        for (int i = 0; i < tooltip.size(); i++) {
            if(tooltip.get(i).contains("Click to remove!")) {
                soul = true;
            }
        }
        if(!soul) {
            return;
        }
        if(confirmedSlot == null) {
            confirmedSlot = slot;
            expirationTime = System.currentTimeMillis()+EXPIRATION_DELAY;
            sendConfirmMessage();
            event.setCanceled(true);
            return;
        }
        if (confirmedSlot == slot) {
            if (expirationTime <= System.currentTimeMillis()) {
                event.setCanceled(true);
                sendConfirmMessage();
                expirationTime = System.currentTimeMillis()+EXPIRATION_DELAY;
                return;
            }
            return;
        }
        event.setCanceled(true);
        sendConfirmMessage();
        confirmedSlot = slot;
        expirationTime = System.currentTimeMillis()+EXPIRATION_DELAY;


    }

    public void sendConfirmMessage() {
        ChatHandler.sendMessage("Please click again within 2 seconds to remove this soul!", MessageType.WARNING);
        Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, 1.5f);
    }

}
