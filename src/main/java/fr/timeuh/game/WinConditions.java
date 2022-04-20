package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class WinConditions {

    private UHCRun uhcRun;

    public WinConditions(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    public void checkWin(){
        if (uhcRun.isState(State.PVP)){
            if (uhcRun.getPlayers().getAlivePlayers().size() == 1){
                Player winner = uhcRun.getPlayers().allLivePlayers().get(0);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La partie est terminée, " + ChatColor.DARK_RED + ChatColor.BOLD + winner.getName() + ChatColor.GOLD + " a gagné");
                Stop stop = new Stop(uhcRun);
                stop.runTaskTimer(uhcRun, 0, 20);
            } else if (uhcRun.getPlayers().getAlivePlayers().size() - uhcRun.getPlayers().getDecoPlayers().size() == 1){
                Player winner = getWinnerDeco();
                if (winner != null) {
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La partie est terminée, " + ChatColor.DARK_RED + ChatColor.BOLD + winner.getName() + ChatColor.GOLD + " a gagné");
                }
                Stop stop = new Stop(uhcRun);
                stop.runTaskTimer(uhcRun, 0, 20);
            }
        }
    }

    private Player getWinnerDeco(){
        List<Player> activePlayers = uhcRun.getPlayers().allLivePlayers();
        List<Player> inactivePlayers = uhcRun.getPlayers().allDecoPlayers();

        for (Player player : activePlayers){
            if (!inactivePlayers.contains(player)){
                return player;
            }
        }
        return null;
    }
}
