package me.katnissali.playertracker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            List<String> list = new ArrayList<String>();
            if(sender.hasPermission("PlayerTracker.help"))   list.add("help");
            if(sender.hasPermission("PlayerTracker.give-compass"))   list.add("give");
            return list.stream().filter(a -> a.toLowerCase().startsWith(args[args.length-1].toLowerCase())).collect(Collectors.toList());
        } else if(args.length == 2){
            List<String> list = new ArrayList<String>();
            if(sender.hasPermission("PlayerTracker.give-compass")){
                for(Player player : Bukkit.getOnlinePlayers()){
                    list.add(player.getName());
                }
            }
            return list.stream().filter(a -> a.toLowerCase().startsWith(args[args.length-1].toLowerCase())).collect(Collectors.toList());
        }

        return null;
    }
}
