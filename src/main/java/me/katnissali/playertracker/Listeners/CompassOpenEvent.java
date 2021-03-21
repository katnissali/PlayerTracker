package me.katnissali.playertracker.Listeners;

import Core.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassOpenEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(player.getItemInHand().isSimilar(Util.getCompassManager().getCompass())) {
            if (player.hasPermission("PlayerTracker.use-tracker")) {
                player.openInventory(Util.getGuiManager().getInventory());
            } else {
                Util.noPermission(player);
            }
        }
    }

}
