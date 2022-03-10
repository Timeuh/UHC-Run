package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class GameCycle  extends BukkitRunnable {

    private UHCRun uhcRun;
    private PlayerTeams teams;
    private static int timer = 30;

    public GameCycle(UHCRun uhcRun, PlayerTeams teams) {
        this.uhcRun = uhcRun;
        this.teams = teams;
    }


    @Override
    public void run() {
        WorldBorder border = Bukkit.getWorld("world").getWorldBorder();

        if (uhcRun.isState(GameState.PLAYING)){
            if (timer == 30 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
            }
            for (Player player : uhcRun.getAlivePlayers()) {
                uhcRun.createBoard(teams);
            }
            switch(timer){
                case 600:
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + 10 + ChatColor.GOLD + " minutes");
                    break;

                case 300:
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + 5 + ChatColor.GOLD + " minutes");
                    break;

                case 120:
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + 2 + ChatColor.GOLD + " minutes");
                    break;

                case 60:
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + 1 + ChatColor.GOLD + " minute");
                    break;

                case 0:
                    int i = 0;
                    for (Player player : uhcRun.getAlivePlayers()) {
                        uhcRun.createPVPBoard(player, teams);
                        player.teleport(uhcRun.getPvp().get(i));
                        uhcRun.beInsensible(player);
                        i++;
                    }
                    uhcRun.checkWin(uhcRun, teams);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Teleportation finale");
                    uhcRun.setState(GameState.FIGHTING);
                    break;
            }
        }

        if (uhcRun.isState(GameState.FIGHTING)) {
            if (border.getSize() > 250) {
                border.setSize(250, 300);
                border.setDamageAmount(2);
                border.setDamageBuffer(2);
            }
        }
        timer--;
    }

    public static int getTimer(){
        return timer;
    }

    public static void resetTimer(){
        timer = 30;
    }
}
