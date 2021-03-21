package me.katnissali.playertracker.Managers;

import Core.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TrackingManager {

    private HashMap<String, String> compasses = new HashMap();

    public TrackingManager(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Util.getMain(), new Runnable() {
            @Override
            public void run() {
                Util.getGuiManager().refreshInventory();

                for(String p : compasses.keySet()){
                    Player player = Bukkit.getPlayer(p);
                    Player victim = Bukkit.getPlayer(compasses.get(p));
                    double distance = player.getLocation().distance(victim.getLocation());
                    String message = "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + victim.getName() + "         " + (int) distance + " blocks";
                    player.setCompassTarget(victim.getLocation());
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                }
            }
        }, 20L, 20L);
    }

    public void addPlayer(Player player, Player target){
        while(compasses.containsKey(player)){
            compasses.remove(player);
        }
        compasses.put(player.getName(), target.getName());
    }
    public void removePlayer(Player player){
        while (compasses.containsKey(player.getName())) {
            compasses.remove(player.getName());
        }
    }
    public String getTracking(Player player){
        return compasses.get(player.getName());
    }
    public void removeVictim(Player victim){
        for(String player : compasses.keySet()){
            if(compasses.get(player).equals(victim.getName())){
                compasses.remove(player);
                Bukkit.getPlayer(player).sendMessage(Util.coloredConfigString("messages.player-left-no-longer-tracking").replace("%victim%", victim.getName()));
            }
        }
    }


    public void changedWorld(Player victim){
        for(String p : compasses.keySet()){
            if(compasses.get(p).equals(victim.getName())){
                Player player = Bukkit.getPlayer(p);
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
                player.sendMessage(Util.coloredConfigString("messages.victim-world-change").replace("%world%", world).replace("%victim%", victim.getName()));
                //player.sendMessage(Util.getPrefix() + victim.getName() + "went to the " + world + ". Go there to track them again.");
                removePlayer(player);
            }
        }
        while(compasses.containsKey(victim.getName())){
            //victim.sendMessage(Util.getPrefix() + "You are no longer tracking " + compasses.get(victim.getName()) + " because you changed worlds.");
            victim.sendMessage(Util.coloredConfigString("messages.player-world-change").replace("%victim%", compasses.get(victim.getName())));
            compasses.remove(victim.getName());
        }
    }

}
