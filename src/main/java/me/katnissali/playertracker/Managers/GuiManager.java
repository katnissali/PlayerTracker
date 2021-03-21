package me.katnissali.playertracker.Managers;

import Core.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private Inventory inventory;

    public GuiManager(){
        inventory = getTemplate();
        refreshInventory();
    }

    //  GETTERS
    private Inventory getTemplate(){
        Inventory inv = Bukkit.createInventory(null, 54, "Select a player");

        //  PANES
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        for(int i = 0; i < 9; i++){
            inv.setItem(i, pane);
        }
        for(int i = 45; i < 54; i++){
            inv.setItem(i, pane);
        }

        //  COMPASS
        ItemStack compass = new ItemStack(Material.COMPASS);
        meta = compass.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName("" + ChatColor.LIGHT_PURPLE + "PlayerTracker");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.GRAY + "(Click to track a player)");
        lore.add(ChatColor.GRAY + " ");
        lore.add(ChatColor.GRAY + "If you and your target");
        lore.add(ChatColor.GRAY + "are not in the same world");
        lore.add(ChatColor.GRAY + "use the action bar too see");
        lore.add(ChatColor.GRAY + "how far apart you are.");

        meta.setLore(lore);
        compass.setItemMeta(meta);
        inv.setItem(4, compass);

        //  BARRIER
        ItemStack barrier = new ItemStack(Material.BARRIER);
        meta = barrier.getItemMeta();
        meta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "Stop tracking");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        barrier.setItemMeta(meta);
        inv.setItem(49, barrier);

        return inv;
    }
    public Inventory getInventory(){ return inventory; }

    //  SETTERS
    public void refreshInventory(){
        inventory = getTemplate();
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission("PlayerTracker.be-tracked")) {
                ItemStack skull = Util.getPlayerHead(player);
                inventory.addItem(skull);
            }
        }
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getOpenInventory().getTitle().equalsIgnoreCase("Select a player")){
                player.openInventory(inventory);
            }
        }
    }
    public void resetInventory(){ inventory = getTemplate(); }

}
