package de.lukaz.necromancyplus.enums;

import net.minecraft.util.EnumChatFormatting;

public enum MessageType {

    INFO(EnumChatFormatting.GREEN), WARNING(EnumChatFormatting.YELLOW), ERROR(EnumChatFormatting.DARK_RED), DEBUG(EnumChatFormatting.BLUE);

    public EnumChatFormatting color;

    MessageType(EnumChatFormatting color) {
        this.color = color;
    }

}
