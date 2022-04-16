package fr.timeuh.commands;

import fr.timeuh.UHCRun;
import fr.timeuh.game.Start;
import fr.timeuh.game.State;
import fr.timeuh.game.Stop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private UHCRun uhcRun;

    public Commands(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            switch(command.getName()){
                case "start":
                    Start start = new Start(uhcRun);
                    start.runTaskTimer(uhcRun, 0, 20);
                    return true;

                case "gstop":
                    Stop stop = new Stop(uhcRun);
                    stop.runTaskTimer(uhcRun, 0, 20);
                    return true;

                case "setState":
                    switch (args[0]){
                        case "waiting":
                            uhcRun.setState(State.WAITING);
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "WAITING");
                            break;
                        case "starting":
                            uhcRun.setState(State.STARTING);
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "STARTING");
                            break;
                        case "playing":
                            uhcRun.setState(State.PLAYING);
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "PLAYING");
                            break;
                        case "pvp":
                            uhcRun.setState(State.PVP);
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "PVP");
                            break;
                        case "finish":
                            uhcRun.setState(State.FINISH);
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "FINISH");
                            break;
                    }

            }
        }
        return false;
    }
}
