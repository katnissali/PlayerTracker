package me.katnissali.playertracker.Managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CompassManager {

    private ItemStack compass;

    public CompassManager(){
        compass = getTemplateCompass();
    }

    private ItemStack getTemplateCompass(){
        ItemStack compass = new ItemStack(Material.COMPASS);
        compass.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        ItemMeta meta = compass.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "COMPASS");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.GRAY + "(Right-Click to track)");
        meta.setLore(lore);
        compass.setItemMeta(meta);
        return compass;
    }
    public ItemStack getCompass(){ return compass; }
}
