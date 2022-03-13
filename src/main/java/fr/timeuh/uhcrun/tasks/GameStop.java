package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStop extends BukkitRunnable {

    private UHCRun uhcRun;
    private int timer = 20;

    public GameStop(UHCRun UHCRun){
        this.uhcRun = UHCRun;
    }

    @Override
    public void run() {
        uhcRun.setState(GameState.FINISH);

        if (timer == 20 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Arrêt dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
        }

        if (timer == 0){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Arrêt de la partie");
            for (Player player : uhcRun.getPlayers()){
                Location spawn = new Location(player.getWorld(), 0, 100, 0);
                uhcRun.beInsensible(player);
                player.teleport(spawn);
                player.getInventory().clear();
                player.setStatistic(Statistic.PLAYER_KILLS, 0);
                uhcRun.createLobbyBoard(player);
                PlayerTeams.leaveTeam(player, uhcRun);

                if (player.getGameMode() == GameMode.SPECTATOR){
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
            uhcRun.getAlivePlayers().remove(uhcRun.getAlivePlayers().get(0));
            WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
            border.setSize(3000);
            uhcRun.setState(GameState.WAITING);
            cancel();
        }
        timer--;
    }
}
