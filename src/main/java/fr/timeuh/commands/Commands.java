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
                    start(args, player);
                    return true;

                case "gstop":
                    stop(args, player);
                    return true;

                case "setState":
                    if (args.length == 0){
                        helpSetState(player);
                        return false;
                    } else {
                        setState(args[0], args, player);
                        return true;
                    }

                case "scenario":
                    displayScenarios(player);
                    return true;

                case "help":
                    help(player);
                    return true;

                case "commandHelp":
                    commandHelp(player);
                    return true;

                case "scenarioHelp":
                    scenarioHelp(player);
                    return true;
            }
        }
        return false;
    }

    private void start(String[] args, Player player){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/start <Pas d'arguments>");
        } else {
            Start start = new Start(uhcRun);
            start.runTaskTimer(uhcRun, 0, 20);
        }
    }

    private void stop(String[] args, Player player){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/gstop <Pas d'arguments>");
        } else {
            Stop stop = new Stop(uhcRun);
            stop.runTaskTimer(uhcRun, 0, 20);
        }
    }

    private void setState(String state, String[] args, Player player){
        if (wrongLengthOneArgument(args) || state == null){
            helpSetState(player);
        } else {
            switch (state) {
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

                default:
                    helpSetState(player);
            }
        }
    }

    private boolean wrongLengthNoArguments(String[] args){
        return args.length > 0;
    }

    private boolean wrongLengthOneArgument(String[] args){
        return args.length != 1;
    }

    private void helpSetState(Player player){
        player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[setState/ss] <waiting/starting/playing/pvp/finish>");
        player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "⚠ Cette commande est utilisée en développement pour les tests, elle n'est pas destinée à être utilisée pour les parties");
    }

    private void displayScenarios(Player player){
        player.sendMessage(ChatColor.GOLD + "Commande en construction");
    }

    private void help(Player player){
        player.sendMessage(ChatColor.GOLD + "Commande en construction");
    }

    private void commandHelp(Player player){
        player.sendMessage(ChatColor.GOLD + "Commande en construction");
    }

    private void scenarioHelp(Player player){
        player.sendMessage(ChatColor.GOLD + "Commande en construction");
    }
}
