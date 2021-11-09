package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class GetArmorCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "getarmor";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return null;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        ChatHandler.sendMessage(Minecraft.getMinecraft().thePlayer.inventory.armorInventory[0].getTagCompound().toString(), MessageType.DEBUG);
    }
}
