package me.katnissali.playertracker.Commands;

import Core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class CompassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //  COMMAND:
        //  compass give <playername>

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0) {
                if (player.hasPermission("PlayerTracker.get-compass")) {
                    player.sendMessage(Util.coloredConfigString("messages.self-gave-compass"));
                    player.getInventory().addItem(Util.getCompassManager().getCompass());
                } else {
                    Util.noPermission(player);
                }
            } else {
                if(args.length == 2) {
                    if (args[0].equalsIgnoreCase("give")) {
                        if (player.hasPermission("PlayerTracker.give-compass")) {
                            Player reciever = Bukkit.getPlayer(args[1]);
                            if (reciever != null) {
                                player.sendMessage(Util.getPrefix() + "Gave " + args[1] + " a compass.");
                                reciever.sendMessage(Util.coloredConfigString("messages.other-gave-compass"));
                                reciever.getInventory().addItem(Util.getCompassManager().getCompass());
                            } else {
                                player.sendMessage(ChatColor.RED + "Invalid playername!");
                            }
                        } else {
                            Util.noPermission(player);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid command. Use /compass help.");
                    }
                } else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("help")) {
                        if (player.hasPermission("PlayerTracker.help")) {
                            sendHelpMessage(player);
                        } else {
                            Util.noPermission(player);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Invalid command. Use /compass help.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid command. Use /compass help.");
                }
            }

        } else {
            if(args.length == 0){
                Util.onlyPlayers();
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player != null) {

                        Util.print(Util.getPrefix() + "Gave " + args[1] + " a compass.");
                        player.sendMessage(Util.coloredConfigString("messages.other-gave-compass"));
                        player.getInventory().addItem(Util.getCompassManager().getCompass());

                    } else {
                        Util.print(ChatColor.RED + "Invalid playername!");
                    }
                } else {
                    Util.print(ChatColor.RED + "Invalid command. Use /compass help.");
                }
            } else if(args.length == 1){
                if(args[0].equalsIgnoreCase("help")){
                    sendHelpMessage();
                } else {
                    Util.print(ChatColor.RED + "Invalid command. Use /compass help.");
                }
            } else {
                Util.print(ChatColor.RED + "Invalid command. Use /compass help.");
            }
        }

        return false;
    }


    private void sendHelpMessage(Player player){
        player.sendMessage("");
        player.sendMessage("" + ChatColor.AQUA + ChatColor.UNDERLINE + "______________ [PlayerTracker] ______________");
        player.sendMessage("");player.sendMessage("");

        sendCommandMsg(player, "playertracker help", "This page.", "PlayerTracker.help");
        sendCommandMsg(player, "compass give <playername>", "Give a compass.", "PlayerTracker.give-compass");
        sendCommandMsg(player, "compass", "Get a compass.", "PlayerTracker.get-compass");

        player.sendMessage("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "Open tracker GUI");
        player.sendMessage("" + ChatColor.GRAY + "       Permission: " + "PlayerTracker.use-tracker");

        player.sendMessage("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "Be tracked (included in GUI)");
        player.sendMessage("" + ChatColor.GRAY + "       Permission: " + "PlayerTracker.be-tracked");

        player.sendMessage("" + ChatColor.AQUA + ChatColor.UNDERLINE + "___________________________________________");
    }
    private void sendHelpMessage(){
        Util.print("");
        Util.print("" + ChatColor.AQUA + ChatColor.UNDERLINE + "_______________ [PlayerTracker] _______________");
        Util.print("");

        sendCommandMsg("playertracker help", "This page.", "PlayerTracker.help");
        sendCommandMsg("compass give <playername>", "Give a compass.", "PlayerTracker.give-compass");
        sendCommandMsg("compass", "Get a compass.", "PlayerTracker.get-compass");

        Util.print("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "Open tracker GUI");
        Util.print("" + ChatColor.GRAY + "       Permission: " + "PlayerTracker.use-tracker");
        Util.print("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "Be tracked (included in GUI)");
        Util.print("" + ChatColor.GRAY + "       Permission: " + "PlayerTracker.be-tracked");


        Util.print("" + ChatColor.AQUA + ChatColor.UNDERLINE + "_____________________________________________");

    }

    private void sendCommandMsg(Player player, String command, String description, String permission){
        player.sendMessage("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "/" + command);
        player.sendMessage("" + ChatColor.GRAY + "       " + description);
        player.sendMessage("" + ChatColor.GRAY + "       Permission: " + permission);
    }
    private void sendCommandMsg(String command, String description, String permission){
        Util.print("" + ChatColor.GOLD + "     - " + ChatColor.GRAY + "/" + command);
        Util.print("" + ChatColor.GRAY + "       " + description);
        Util.print("" + ChatColor.GRAY + "       Permission: " + permission);
    }


}
