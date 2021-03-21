package me.katnissali.playertracker.Listeners;

import Core.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryRefreshEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        refresh();
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Util.getTrackingManager().removeVictim(e.getPlayer());
        refresh();
    }
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Util.getTrackingManager().changedWorld(e.getPlayer());
        refresh();
    }

    private void refresh(){
        Util.getGuiManager().refreshInventory();
    }
}
