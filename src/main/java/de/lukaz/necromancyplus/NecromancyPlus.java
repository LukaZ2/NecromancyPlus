package de.lukaz.necromancyplus;

import de.lukaz.necromancyplus.commands.*;
import de.lukaz.necromancyplus.features.ManaCostViewer;
import de.lukaz.necromancyplus.features.DroppedSoulInfo;
import de.lukaz.necromancyplus.handlers.SettingsHandler;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.utils.MessageType;
import de.lukaz.necromancyplus.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = NecromancyPlus.MODID, version = NecromancyPlus.VERSION, clientSideOnly = true)
public class NecromancyPlus
{
    public static final String MODID = "NecromancyPlus";

    public static final String VERSION = "1.0";

    public static GuiScreen guiScreenToOpen;

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ManaCostViewer());
        MinecraftForge.EVENT_BUS.register(new DroppedSoulInfo());

    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ClientCommandHandler.instance.registerCommand(new GetNBTCommand());
        ClientCommandHandler.instance.registerCommand(new GetEntityNBTCommand());
        ClientCommandHandler.instance.registerCommand(new MenuCommand());
        ClientCommandHandler.instance.registerCommand(new GetTalismanJsonCommand());
        ClientCommandHandler.instance.registerCommand(new GetArmorCommand());
        ClientCommandHandler.instance.registerCommand(new GetPetsCommand());

    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if(guiScreenToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiScreenToOpen);
            guiScreenToOpen = null;
        }
    }

    @SubscribeEvent
    public void apiKeyChecker(ClientChatReceivedEvent event) {
        String message = Utils.clearColour(event.message.toString());

        if(message.contains("Your new API key is")) {
            String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
            SettingsHandler.setStringValue("strings", "api", apiKey);
            ChatHandler.sendMessage("API key has been set.", MessageType.INFO);
        }
    }
}