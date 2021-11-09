package de.lukaz.necromancyplus.commands;

import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class GetEntityNBTCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "getentitynbt";
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

        if(strings.length != 1) {
            ChatHandler.sendMessage("Wrong usage! Please use: /getentitynbt <Radius>", MessageType.ERROR);
            return;
        }
        int r;
        try {
            r = Integer.parseInt(strings[0]);
        } catch (Exception e) {
            ChatHandler.sendMessage(strings[0] + " is not a Number!", MessageType.ERROR);
            return;
        }
        if(r < 1) {
            ChatHandler.sendMessage("The radius cannot be 0 or below!", MessageType.ERROR);
            return;
        }

        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;

        double x = entityPlayerSP.posX;
        double y = entityPlayerSP.posY;
        double z = entityPlayerSP.posZ;

        AxisAlignedBB scan = new AxisAlignedBB(x-r, y-r, z-r, x+r, y+r, z+r);
        List<Entity> entities = worldClient.getEntitiesWithinAABB(Entity.class, scan);

        ChatHandler.sendMessage("(SCAN COMPLETE) Found " + entities.size() + " entities.", MessageType.INFO);
        for (Entity entity : entities) {
            ChatHandler.sendMessage(entity.getClass() +": " + entity.getInventory()[4].getItem().getClass().toString(), MessageType.DEBUG);
        }
    }
}
