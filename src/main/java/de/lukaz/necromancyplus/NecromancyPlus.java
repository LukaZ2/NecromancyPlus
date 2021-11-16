package de.lukaz.necromancyplus;

import com.google.gson.JsonObject;
import de.lukaz.necromancyplus.commands.*;
import de.lukaz.necromancyplus.features.ManaCostViewer;
import de.lukaz.necromancyplus.features.DroppedSoulInfo;
import de.lukaz.necromancyplus.features.RemoveConfirm;
import de.lukaz.necromancyplus.handlers.APIHandler;
import de.lukaz.necromancyplus.handlers.SettingsHandler;
import de.lukaz.necromancyplus.handlers.ChatHandler;
import de.lukaz.necromancyplus.enums.MessageType;
import de.lukaz.necromancyplus.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.net.URL;

@Mod(modid = NecromancyPlus.MODID, version = NecromancyPlus.VERSION, clientSideOnly = true)
public class NecromancyPlus
{
    public static final String MODID = "NecromancyPlus";

    public static final String VERSION = "1.0";

    public static GuiScreen guiScreenToOpen;

    public static boolean updateChecked;

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        SettingsHandler.init();
        Utils.refreshApi();
        updateChecked = false;
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ManaCostViewer());
        MinecraftForge.EVENT_BUS.register(new DroppedSoulInfo());
        MinecraftForge.EVENT_BUS.register(new RemoveConfirm());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ClientCommandHandler.instance.registerCommand(new MenuCommand());

    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if(guiScreenToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiScreenToOpen);
            guiScreenToOpen = null;
        }
    }

    @SubscribeEvent
    public void apiKeyEvent(ClientChatReceivedEvent event) {
        String message = Utils.clearColour(event.message.toString());

        if(message.contains("Your new API key is")) {
            String apiKey = event.message.getSiblings().get(0).getChatStyle().getChatClickEvent().getValue();
            SettingsHandler.setStringValue("strings", "api", apiKey);
            Utils.refreshApi();
            ChatHandler.sendMessage("API key has been set.", MessageType.INFO);
        }
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if(updateChecked) {
            return;
        }
        updateChecked = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObject response = null;
                try {
                    response = APIHandler.get(new URL("https://api.github.com/repos/LukaZ2/NecromancyPlus/releases/latest"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response == null) {
                    ChatHandler.sendMessage("Failed to check updates!", MessageType.ERROR);
                    return;
                }
                if(response.get("tag_name").getAsString().equals(VERSION)) {
                    return;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ChatHandler.sendMessage(" ", MessageType.ERROR);
                ChatHandler.sendMessage("The NecromancyPlus mod is outdated!", MessageType.ERROR);
                ChatHandler.sendMessage("Please update it for new features and bugfixes!", MessageType.ERROR);
                ChatHandler.sendMessage(response.get("html_url").getAsString(), MessageType.ERROR);
                ChatHandler.sendMessage(" ", MessageType.ERROR);
            }
        }).start();

    }
}