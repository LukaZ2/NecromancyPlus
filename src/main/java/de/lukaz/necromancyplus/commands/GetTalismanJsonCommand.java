package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.handlers.APIHandler;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class GetTalismanJsonCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "gettalismans";
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
        ChatHandler.sendMessage(APIHandler.getTalismans(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).toString(), MessageType.DEBUG);
    }
}
