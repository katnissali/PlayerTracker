package me.katnissali.playertracker.Listeners;

import Core.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player player = (Player) e.getWhoClicked();
            if(player.getOpenInventory().getTitle().equals("Select a player")){
                e.setCancelled(true);
                ItemStack item = e.getCurrentItem();
                if(item != null && !item.getType().equals(Material.AIR)){
                    if(item.getType().equals(Material.PLAYER_HEAD)){
                        Player victim = Bukkit.getPlayer(((SkullMeta) item.getItemMeta()).getOwningPlayer().getName());
                        if(victim == null){
                            player.sendMessage(ChatColor.RED + "Internal error! Please try again. If this issue persists, please contact server staff.");
                            return;
                        }
                        if(!victim.getName().equals(player.getName())) {
                            if(player.getWorld().equals(victim.getWorld())) {
                                if(Util.getTrackingManager().getTracking(player) == null || !Util.getTrackingManager().getTracking(player).equals(victim.getName())) {
                                    Util.getTrackingManager().addPlayer(player, victim);
                                    player.sendMessage(Util.coloredConfigString("messages.now-tracking").replace("%victim%", victim.getName()));
                                    player.closeInventory();
                                } else {
                                    player.sendMessage(Util.coloredConfigString("messages.already-tracking").replace("%player%", victim.getName()));
                                    player.closeInventory();
                                }
                            } else {
                                String world = "";
                                switch(victim.getWorld().getEnvironment()){
                                    case NORMAL:
                                        world = Util.coloredConfigString("world-names.overworld");
                                        break;
                                    case THE_END:
                                        world = Util.coloredConfigString("world-names.the-end");
                                        break;
                                    case NETHER:
                                        world = Util.coloredConfigString("world-names.nether");
                                        break;
                                }
                                player.sendMessage(Util.coloredConfigString("messages.go-to-world-to-track").replace("%world%", world));
                                player.closeInventory();
                                return;
                            }
                        } else {
                            player.sendMessage(Util.coloredConfigString("messages.cant-track-self"));
                            player.closeInventory();
                        }
                    } else if(item.getType().equals(Material.BARRIER)){
                        if(Util.getTrackingManager().getTracking(player) == null){
                            player.sendMessage(Util.coloredConfigString("messages.not-tracking-anybody"));
                            player.closeInventory();
                        } else {
                            player.sendMessage(Util.coloredConfigString("messages.stopped-tracking-player").replace("%victim%", Util.getTrackingManager().getTracking(player)));
                            Util.getTrackingManager().removePlayer(player);
                            player.closeInventory();
                        }
                    }
                }

            }
        }
    }

}
