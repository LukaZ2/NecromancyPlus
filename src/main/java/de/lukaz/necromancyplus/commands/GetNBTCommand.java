package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;

public class GetNBTCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "getnbt";
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
        if(Minecraft.getMinecraft().thePlayer.getHeldItem() == null) {
            ChatHandler.sendMessage("Please hold an item for this command to work!", MessageType.ERROR);
            return;
        }
        NBTTagCompound nbtTagCompound = Minecraft.getMinecraft().thePlayer.getHeldItem().getTagCompound();
        if(nbtTagCompound == null) {
            ChatHandler.sendMessage("The item you are holding does not have a NBT Tag!", MessageType.ERROR);
            return;
        }
        ChatHandler.sendMessage(nbtTagCompound.toString(), MessageType.INFO);
    }
}
