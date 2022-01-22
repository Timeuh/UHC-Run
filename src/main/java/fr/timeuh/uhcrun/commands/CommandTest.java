package fr.timeuh.uhcrun.commands;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.start.GameAutoStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTest implements CommandExecutor {

    private UHCRun UHCRun;

    public CommandTest(UHCRun UHCRun) {
        this.UHCRun = UHCRun;
    }

    public CommandTest() {
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Location spawn = new Location(player.getWorld(), 0, 65, 0);

            switch(command.getName()){
                case "test":
                    player.sendMessage(ChatColor.AQUA + UHCRun.getConfig().getString("messages.test"));
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
                    player.sendMessage("Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "gamestop":
                    UHCRun.setState(GameState.WAITING);
                    player.setLevel(0);
                    player.sendMessage("Arrêt de la partie ...");
                    player.sendMessage("Téléportation au spawn...");
                    player.teleport(spawn);
                    return true;

                case "gamestart":
                    GameAutoStart start = new GameAutoStart(UHCRun);
                    start.runTaskTimer(UHCRun, 0, 20);
                    UHCRun.setState(GameState.STARTING);
                    return true;

                default: break;
            }
        }
        return false;
    }
}
