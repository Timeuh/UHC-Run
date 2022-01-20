package com.timeuh.dsuhc.commands;

import com.timeuh.dsuhc.DemonSlayerUHC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTest implements CommandExecutor {

    private DemonSlayerUHC main;
    public CommandTest(DemonSlayerUHC demonSlayerUHC) {
        this.main = demonSlayerUHC;
    }

    public CommandTest() {
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            switch(command.getName()){
                case "test":
                    player.sendMessage(ChatColor.AQUA + main.getConfig().getString("messages.test"));
                    return true;

                case "gamebroadcast":
                    if(args.length == 0){
                        player.sendMessage("l'utilisation correcte de cette commande est : /gamebroadcast <message>");
                    }

                    if(args.length > 1){
                        StringBuilder broadcast = new StringBuilder();
                        for (String partie : args){
                            broadcast.append(partie + " ");
                        }
                        Bukkit.broadcastMessage(ChatColor.RED+"MESSAGE : "+ChatColor.DARK_RED+broadcast.toString());
                    }
                    return true;

                case "spawn":
                    Location spawn = new Location(player.getWorld(), 0, 65, 0);
                    player.sendMessage("Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                default: break;
            }
        }
        return false;
    }
}
