package fr.timeuh.uhcrun.commands;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.tasks.GameStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommands implements CommandExecutor {

    private UHCRun uhcRun;

    public GameCommands(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
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
                        player.sendMessage("l'utilisation correcte de cette commande est : /broadcast <message>");
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
                    player.sendMessage("Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "stop":
                    uhcRun.setState(GameState.FINISH);
                    player.setLevel(0);
                    player.sendMessage("Arrêt de la partie ...");
                    player.sendMessage("Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "start":
                    GameStart start = new GameStart(uhcRun);
                    start.runTaskTimer(uhcRun, 0, 20);
                    uhcRun.setState(GameState.STARTING);
                    return true;

                default: break;
            }
        }
        return false;
    }
}
