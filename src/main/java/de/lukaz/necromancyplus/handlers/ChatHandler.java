package de.lukaz.necromancyplus.handlers;

import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatHandler {

    public static final String PREFIX = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.BOLD + "N+" + EnumChatFormatting.GRAY + "] ";

    public static void sendMessage(String message, MessageType type) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PREFIX + type.color + message));
    }

}
