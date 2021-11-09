package de.lukaz.necromancyplus.features;

import de.lukaz.necromancyplus.utils.Module;
import de.lukaz.necromancyplus.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemSkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class DroppedSoulInfo {

    public static final int RENDER_RADIUS = 20;
    public List<Entity> dead_entities;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if(Module.DISPLAY_DROPPED_SOULS.getState() == 0) {
            return;
        }

        if(dead_entities == null) {
            dead_entities = new ArrayList();
        }

        double x = event.player.posX;
        double y = event.player.posY;
        double z = event.player.posZ;

        int r = RENDER_RADIUS;

        AxisAlignedBB scan = new AxisAlignedBB(x+r, y+r, z+r,x-r, y-r, z-r);
        for(final Entity entity : Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(Entity.class, scan)) {
            if (entity.hasCustomName()) {
                        String entityName_cleared = Utils.clearColour(entity.getCustomNameTag());
                        if (entityName_cleared.contains(" 0/") || entity.hurtResistantTime != 0) {
                            final double final_e_x = entity.prevPosX;
                            final double final_e_y = entity.prevPosY;
                            final double final_e_z = entity.prevPosZ;
                            String entitySoulDisplayRaw;
                            String entitySoulLevelDisplay;
                            String entitySoulNameDisplay;
                            String entitySoulMaxHealthDisplay;
                            if(entityName_cleared.contains("/")) {
                                entitySoulDisplayRaw = entityName_cleared.split("/")[0];
                                entitySoulDisplayRaw = entitySoulDisplayRaw.replace(" 0", "");
                                entitySoulLevelDisplay = entitySoulDisplayRaw.split("]")[0].replace("[", "");
                                entitySoulNameDisplay = entitySoulDisplayRaw.split("]")[1].substring(1);
                                entitySoulMaxHealthDisplay = "";
                                try {
                                    entitySoulMaxHealthDisplay = entityName_cleared.split("/")[1];
                                } catch (IndexOutOfBoundsException e) { }
                            } else {
                                if(!entity.isDead) {
                                    return;
                                }
                                entitySoulDisplayRaw = entityName_cleared.split(" 0")[0];
                                entitySoulLevelDisplay = "Lvl1";
                                entitySoulNameDisplay = entitySoulDisplayRaw;
                                entitySoulMaxHealthDisplay = "";
                            }
                                entitySoulDisplayRaw = EnumChatFormatting.DARK_AQUA + "© " + EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GRAY + entitySoulLevelDisplay + EnumChatFormatting.DARK_GRAY + "] " + EnumChatFormatting.RED + entitySoulNameDisplay + " " + EnumChatFormatting.GREEN + entitySoulMaxHealthDisplay;
                                final String entitySoulDisplay = entitySoulDisplayRaw.replace("Runic ", "");
                                if (!dead_entities.contains(entity)) {
                                    int id = entity.getEntityId();
                                    dead_entities.add(entity);

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(50);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            dead_entities.remove(entity);
                                            AxisAlignedBB scan = new AxisAlignedBB(final_e_x + 0.3, final_e_y + 2, final_e_z + 0.3, final_e_x - 0.3, final_e_y - 4, final_e_z - 0.3);
                                            for (EntityArmorStand scannedEntity : Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, scan)) {
                                                if (scannedEntity.getInventory().length == 5) {
                                                    if (scannedEntity.getInventory()[4].getItem() instanceof ItemSkull) {
                                                        scannedEntity.setAlwaysRenderNameTag(true);
                                                        scannedEntity.setCustomNameTag(entitySoulDisplay);
                                                    }
                                                }
                                            }
                                        }
                                    }).start();
                                }
                            }
                    }
                }
        }
    }



