package fr.timeuh.uhcrun.commands;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.tasks.GameStart;
import fr.timeuh.uhcrun.tasks.GameStop;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {

    private UHCRun uhcRun;
    private PlayerTeams teams;

    public GameCommands(UHCRun uhcRun, PlayerTeams teams) {
        this.uhcRun = uhcRun;
        this.teams = teams;
    }

    public GameCommands() {
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Location spawn = new Location(player.getWorld(), 0, 100, 0);

            switch(command.getName()){
                case "broadcast":
                    if(args.length == 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "l'utilisation correcte de cette commande est : " + ChatColor.DARK_RED + "/[broadcast][bc] <message>");
                    }

                    if(args.length >= 1){
                        StringBuilder broadcast = new StringBuilder();
                        for (String partie : args){
                            broadcast.append(partie + " ");
                        }
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + "MESSAGE : " + ChatColor.GOLD + broadcast);
                    }
                    return true;

                case "spawn":
                    player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "gamestop":
                    GameStop stop = new GameStop(uhcRun, teams);
                    stop.runTaskTimer(uhcRun, 0, 20);
                    return true;

                case "start":
                    if (uhcRun.everyPlayerInTeam()) {
                        GameStart start = new GameStart(uhcRun, teams);
                        start.runTaskTimer(uhcRun, 0, 20);
                        return true;
                    } else {
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Tous les joueurs doivent etre dans une equipe");
                        return false;
                    }

                default: break;
            }
        }
        return false;
    }
}
