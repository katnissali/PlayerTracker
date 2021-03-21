package Core;


import me.katnissali.playertracker.PlayerTracker;
import me.katnissali.playertracker.Managers.CompassManager;
import me.katnissali.playertracker.Managers.GuiManager;
import me.katnissali.playertracker.Managers.TrackingManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private static PlayerTracker main;
    private static GuiManager guiManager;
    private static CompassManager compassManager;
    private static TrackingManager trackingManager;

    public static void setup(PlayerTracker mcc){
        main = mcc;
        guiManager = new GuiManager();
        compassManager = new CompassManager();
        trackingManager = new TrackingManager();
    }
    //  GET CLASS
    public static PlayerTracker getMain(){
        return main;
    }
    public static GuiManager getGuiManager(){
        return guiManager;
    }
    public static CompassManager getCompassManager(){ return compassManager; }
    public static TrackingManager getTrackingManager() { return trackingManager; }

    //  GETTERS
    public static String format(String str){
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public static String getPrefix(){
        return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.prefix"));
        //return (ChatColor.WHITE + "[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "PlayerTracker" + ChatColor.WHITE + "] " + ChatColor.GRAY);
    }
    public static ItemStack getPlayerHead(Player player){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);

        List<String> lore = Util.getMain().getConfig().getStringList("items.gui-player-head-lore");
        meta.setDisplayName(Util.format(lore.get(0).replace("%player-name%", player.getName())));
        lore.remove(0);

        for(int i = 0; i < lore.size(); i++){
            String world = "";
            switch(player.getWorld().getEnvironment()){
                case NORMAL:
                    world = Util.coloredConfigString("world-names.overworld").toUpperCase();
                    break;
                case THE_END:
                    world = Util.coloredConfigString("world-names.the-end").toUpperCase();
                    break;
                case NETHER:
                    world = Util.coloredConfigString("world-names.nether").toUpperCase();
                    break;
            }
            lore.set(i, Util.format(lore.get(i).replace("%world%", world)));
            lore.set(i, Util.format(lore.get(i).replace("%player-name%", player.getName())));
            lore.set(i, Util.format(lore.get(i)));
        }

        meta.setLore(lore);
        head.setItemMeta(meta);

        return head;
    }
    public static String coloredConfigString(String path){
        return format(main.getConfig().getString(path).replace("%prefix%", getPrefix()));
    }
    public static Player getRandomPlayer(){
        for(Player player : Bukkit.getOnlinePlayers()){
            return player;
        }
        return null;
    }


    //  INTERACTIONS
    public static void sendCommand(String str){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str);
    }
    public static void onlyPlayers(){
        print(ChatColor.RED + "Only players can do this.");
    }
    public static void print(String str){
        Bukkit.getServer().getConsoleSender().sendMessage(str);
    }
    public static void clearInventory(Player player){
        player.getInventory().clear();
        if(player.getInventory().getHelmet() != null) {	player.getInventory().getHelmet().setType(Material.AIR);	}
        if(player.getInventory().getChestplate() != null) {	player.getInventory().getChestplate().setType(Material.AIR);	}
        if(player.getInventory().getLeggings() != null) {	player.getInventory().getLeggings().setType(Material.AIR);	}
        if(player.getInventory().getBoots() != null) {	player.getInventory().getBoots().setType(Material.AIR);	}

    }
    public static void noPermission(Player player){
        player.sendMessage(ChatColor.RED + "You do not have permission to do this.");
    }
    public static void triggerEvent(Event e){
        Bukkit.getPluginManager().callEvent(e);
    }
}
