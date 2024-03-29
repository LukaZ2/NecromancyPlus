package de.lukaz.necromancyplus.features.manaoptimizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.lukaz.necromancyplus.handlers.APIHandler;
import de.lukaz.necromancyplus.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManaOptimizer {

    public static List<OptimizerResultText> optimize() {

        List<OptimizerResultText> result = new ArrayList();

        result.add(checkTalismans());
        result.add(checkArmor());
        result.add(checkLevels());
        result.add(checkPet());
        result.add(checkWisdom());
        result.add(checkBigBrain());
        result.add(checkHeroic());
        result.add(checkNecrotic());

        return result;
    }

    public static OptimizerResultText checkTalismans() {
        JsonArray talismans = APIHandler.getTalismans(Minecraft.getMinecraft().thePlayer.getCommandSenderName());

        int talismanCount = talismans.size();
        int talismansReforged = 0;

        for (int i = 0; i < talismanCount; i++) {
            if (talismans.get(i).getAsJsonObject().get("reforge").getAsString().equalsIgnoreCase("bizarre")) {
                talismansReforged++;
            }
        }
        if (talismanCount - talismansReforged != 0) {
            int optimizedMana = 0;
            for (int i = 0; i < talismans.size(); i++) {
                if (!talismans.get(i).getAsJsonObject().get("reforge").getAsString().equalsIgnoreCase("bizarre")) {
                    String rarity = talismans.get(i).getAsJsonObject().get("rarity").getAsString();
                    if(rarity.toLowerCase().equals("common")) {
                        optimizedMana = optimizedMana +6;
                        continue;
                    }
                    if(rarity.toLowerCase().equals("uncommon")) {
                        optimizedMana = optimizedMana+8;
                        continue;
                    }
                    if(rarity.toLowerCase().equals("rare")) {
                        optimizedMana = optimizedMana+10;
                        continue;
                    }
                    if(rarity.toLowerCase().equals("epic")) {
                        optimizedMana = optimizedMana+14;
                        continue;
                    }
                    if(rarity.toLowerCase().equals("legendary")) {
                        optimizedMana = optimizedMana+20;
                        continue;
                    }
                    if(rarity.toLowerCase().equals("mythic")) {
                        optimizedMana = optimizedMana+30;
                    }
                }
            }
            return new OptimizerResultText("You have " + (talismanCount - talismansReforged) + " talismans not reforged to bizarre!", false, String.valueOf(optimizedMana));
        }
        return new OptimizerResultTextSuccess();
    }

    public static OptimizerResultText checkArmor() {
        ItemStack[] armor = Minecraft.getMinecraft().thePlayer.inventory.armorInventory;

        int armor_length = 0;

        for (int i = 0; i < armor.length; i++) {
            if(armor[i] != null) {
                armor_length++;
            }
        }
        if (armor_length != 4) {
            return new OptimizerResultText("You are not wearing a full armor set!", false, "up to approx. 3500");
        }
        return new OptimizerResultTextSuccess();
    }

    public static OptimizerResultText checkWisdom() {
        ItemStack[] armor = Minecraft.getMinecraft().thePlayer.inventory.armorInventory;

        List<ItemStack> noWisdom = new ArrayList();
        int optimizedMana = 0;
        for (int i = 0; i < armor.length; i++) {
            if(armor[i] != null) {
                int level = getEnchantLevel(armor[i], "wisdom");

                if(level != 5) {
                    optimizedMana = optimizedMana + ((5 - level) * 20);
                    noWisdom.add(armor[i]);
                }
            }
        }
        if(noWisdom.size() == 0) {
            return new OptimizerResultTextSuccess();
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < noWisdom.size(); i++) {
            result.append(noWisdom.get(i).getDisplayName()).append(", ");
        }
        result.substring(0, result.length()-2);
        result.append(" don't have Wisdom 5!");
        return new OptimizerResultText(Utils.clearColour(result.toString()), false, String.valueOf(optimizedMana));
    }

    public static OptimizerResultText checkBigBrain() {
        ItemStack helmet = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3];

        int optimizedMana = 0;
        if(helmet != null) {
            int level = getEnchantLevel(helmet, "big brain");

            if(level == 5) {
                return new OptimizerResultTextSuccess();
            }

            optimizedMana = optimizedMana+((5-level)*5);
            return new OptimizerResultText("You dont have Big brain 5 on you helmet!", false, String.valueOf(optimizedMana));
        }
        return null;
    }

    public static OptimizerResultText checkLevels() {
        int level = Minecraft.getMinecraft().thePlayer.experienceLevel;
        if(level < 100) {
            return new OptimizerResultText("You are missing on " + (100-level) + " levels for Wisdom V!", false, String.valueOf((100-level)*4));
        }
        return new OptimizerResultTextSuccess();
    }

    public static OptimizerResultText checkPet() {
        JsonArray pets = null;
        try {
            pets = APIHandler.getProfile(Minecraft.getMinecraft().thePlayer.getCommandSenderName()).get("profiles").getAsJsonObject().
                    get(APIHandler.getCurrentProfile(Minecraft.getMinecraft().thePlayer.getCommandSenderName())).getAsJsonObject().get("raw").getAsJsonObject().get("pets").getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentPet = "";
        String currentPetRarity = "";
        int currentPetLevel = -1;
        for (int i = 0; i < pets.size(); i++) {
            if(pets.get(i).getAsJsonObject().get("active").getAsBoolean()) {
                currentPet = pets.get(i).getAsJsonObject().get("type").getAsString();
                currentPetRarity = pets.get(i).getAsJsonObject().get("tier").getAsString();
                currentPetLevel = pets.get(i).getAsJsonObject().get("level").getAsJsonObject().get("level").getAsInt();
                break;
            }
        }
        if(currentPet.isEmpty()) {
            return new OptimizerResultText("You dont have any pet active!", false, "up to 300");
        }
        if(currentPet.equals("SHEEP")) {
            if(currentPetLevel == 100) {
                return new OptimizerResultTextSuccess();
            }
            return new OptimizerResultText("Your sheep pet is not level 100!", false, String.valueOf((100-currentPetLevel)*2));
        }
        return new OptimizerResultText("You don't have a sheep active!", false, "Up to 200 Mana and 20% less cost");
    }

    public static OptimizerResultText checkHeroic() {
        ItemStack weapon = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if(weapon == null) {
            return new OptimizerResultText("You don't have a weapon in your hand!", false, "Up to around 600");
        }
        if(!weapon.hasTagCompound()) {
            return new OptimizerResultText("Wierd... Your held item does not have a NBT tag.", false, "0");
        }
        List<String> tooltip = weapon.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        if(tooltip.size() == 0) {
            return new OptimizerResultText("Wierd... Your held item does not have a tooltip.", false, "0");
        }
        if(!Utils.clearColour(weapon.getDisplayName()).startsWith("Heroic")) {
            int optimizedMana = 0;
            String rarity = getRarity(weapon);
            if(rarity.equals("COMMON")) {
                optimizedMana = 40;
            }
            if(rarity.equals("UNCOMMON")) {
                optimizedMana = 50;
            }
            if(rarity.equals("RARE")) {
                optimizedMana = 65;
            }
            if(rarity.equals("EPIC")) {
                optimizedMana = 80;
            }
            if(rarity.equals("LEGENDARY")) {
                optimizedMana = 100;
            }
            if(rarity.equals("MYTHIC")) {
                optimizedMana = 125;
            }
            return new OptimizerResultText("Your weapon is not reforged on Heroic!", false, String.valueOf(optimizedMana));
        }
        return new OptimizerResultTextSuccess();
    }

    public static OptimizerResultText checkNecrotic() {
        ItemStack[] armor = Minecraft.getMinecraft().thePlayer.inventory.armorInventory;

        List<ItemStack> noNecrotic = new ArrayList();
        int optimizedMana = 0;
        for (int i = 0; i < armor.length; i++) {
            if(armor[i] != null) {
                if(!Utils.clearColour(armor[i].getDisplayName()).startsWith("Necrotic")) {
                    String rarity = getRarity(armor[i]);
                    if(rarity.equals("COMMON")) {
                        optimizedMana = 30;
                    }
                    if(rarity.equals("UNCOMMON")) {
                        optimizedMana = 60;
                    }
                    if(rarity.equals("RARE")) {
                        optimizedMana = 90;
                    }
                    if(rarity.equals("EPIC")) {
                        optimizedMana = 120;
                    }
                    if(rarity.equals("LEGENDARY")) {
                        optimizedMana = 150;
                    }
                    if(rarity.equals("MYTHIC")) {
                        optimizedMana = 200;
                    }
                    noNecrotic.add(armor[i]);
                }
            }
        }

        if(noNecrotic.size() == 0) {
            return new OptimizerResultTextSuccess();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < noNecrotic.size(); i++) {
            stringBuilder.append(Utils.clearColour(noNecrotic.get(i).getDisplayName())).append(", ");
        }
        String result = stringBuilder.toString().substring(0, stringBuilder.toString().length()-2) + " don't have the Necrotic reforge!";
        return new OptimizerResultText(result, false, String.valueOf(optimizedMana));
    }

    public static int getEnchantLevel(ItemStack item, String enchant) {
        int result = 0;
        List<String> tooltip = item.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        for (int i = 0; i < tooltip.size(); i++) {
            if(Utils.clearColour(tooltip.get(i)).toLowerCase().contains(enchant)) {
                String[] enchants = tooltip.get(i).split(",");
                for (int j = 0; j < enchants.length; j++) {
                    if(Utils.clearColour(enchants[j]).toLowerCase().startsWith(enchant)) {
                        String enchantNameCleared = Utils.clearColour(enchants[j]).toLowerCase();
                        result = Utils.romanNumberToInt(enchantNameCleared.substring(enchant.length()).replaceAll(" ", ""));
                    }
                }
            }
        }
        return result;
    }
    public static String getRarity(ItemStack itemStack) {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if(tagCompound.toString().contains("COMMON")) {
            return "COMMON";
        }
        if(tagCompound.toString().contains("UNCOMMON")) {
            return "UNCOMMON";
        }
        if(tagCompound.toString().contains("RARE")) {
            return "RARE";
        }
        if(tagCompound.toString().contains("EPIC")) {
            return "EPIC";
        }
        if(tagCompound.toString().contains("LEGENDARY")) {
            return "LEGENDARY";
        }
        if(tagCompound.toString().contains("MYTHIC")) {
            return "MYTHIC";
        }
        if(tagCompound.toString().contains("SPECIAL")) {
            return "MYTHIC";
        }
        return "";
    }
}
