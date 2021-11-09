package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.handlers.APIHandler;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.io.IOException;

public class GetPetsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "getpets";
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
        try {
            ChatHandler.sendMessage(APIHandler.getProfile(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).get("profiles").getAsJsonObject().
                    get(APIHandler.getCurrentProfile(Minecraft.getMinecraft().thePlayer.getCommandSenderName())).getAsJsonObject().get("raw").getAsJsonObject().get("pets").getAsJsonArray().toString(), MessageType.DEBUG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
