package me.katnissali.playertracker;

import Core.Util;
import me.katnissali.playertracker.Commands.CompassCommand;
import me.katnissali.playertracker.Commands.TabCompletion;
import me.katnissali.playertracker.Listeners.CompassOpenEvent;
import me.katnissali.playertracker.Listeners.InventoryClick;
import me.katnissali.playertracker.Listeners.InventoryRefreshEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerTracker extends JavaPlugin {

    @Override
    public void onEnable() {
        loadCore();
        Util.print(ChatColor.translateAlternateColorCodes('&',getConfig().getString("messages.prefix")) + "Loading PlayerTracker...");
        loadConfig();
        registerCommands();
        registerEvents();
        Util.print(Util.getPrefix() + "PlayerTracker Loaded.");
    }

    //  TODO upload to git

    private void loadConfig(){
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
    }
    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new CompassOpenEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryRefreshEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
    }
    private void registerCommands(){
        getCommand("PlayerTracker").setExecutor(new CompassCommand());
        getCommand("PlayerTracker").setTabCompleter(new TabCompletion());
    }
    private void loadCore(){
        Util.setup(this);
    }


}
