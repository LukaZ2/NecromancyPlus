package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.NecromancyPlus;
import de.lukaz.necromancyplus.gui.SettingsGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class MenuCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "npmenu";
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
        NecromancyPlus.guiScreenToOpen = new SettingsGui();
    }
}
