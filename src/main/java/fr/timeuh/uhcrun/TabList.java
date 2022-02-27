package fr.timeuh.uhcrun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TabList implements Listener {
    public static void onTab(UHCRun uhcRun){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(uhcRun, new Runnable() {
            Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = null;

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    Player p = player.getPlayer();
                    if (p.isOp()){
                        p.setPlayerListName("§4[OP] §6" + p.getName());
                    } else {
                        p.setPlayerListName("§3[Joueur] §6" + p.getName());
                    }
                }
            }
        }, 0, 3);
    }
}
