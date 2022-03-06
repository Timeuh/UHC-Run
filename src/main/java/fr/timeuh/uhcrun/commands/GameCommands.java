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
                case "test":
                    player.sendMessage(ChatColor.AQUA + uhcRun.getConfig().getString("messages.test"));
                    return true;

                case "broadcast":
                    if(args.length == 0){
                        player.sendMessage("§5[UHCRun] §6l'utilisation correcte de cette commande est : §4/broadcast <message>");
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
                    player.sendMessage("§5[UHCRun] §6Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "gamestop":
                    GameStop stop = new GameStop(uhcRun, teams);
                    stop.runTaskTimer(uhcRun, 0, 20);
                    return true;

                case "start":
                    GameStart start = new GameStart(uhcRun, teams);
                    start.runTaskTimer(uhcRun, 0, 20);
                    return true;

                default: break;
            }
        }
        return false;
    }
}
