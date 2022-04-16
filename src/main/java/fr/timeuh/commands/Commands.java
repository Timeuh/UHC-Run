package fr.timeuh.commands;

import fr.timeuh.UHCRun;
import fr.timeuh.game.Start;
import fr.timeuh.game.State;
import fr.timeuh.game.Stop;
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
                            break;
                        case "starting":
                            uhcRun.setState(State.STARTING);
                            break;
                        case "playing":
                            uhcRun.setState(State.PLAYING);
                            break;
                        case "pvp":
                            uhcRun.setState(State.PVP);
                            break;
                        case "finish":
                            uhcRun.setState(State.FINISH);
                            break;
                    }

            }
        }
        return false;
    }
}
