package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStop extends BukkitRunnable {

    private UHCRun uhcRun;
    private PlayerTeams teams;
    private int timer = 20;

    public GameStop(UHCRun UHCRun, PlayerTeams teams){
        this.uhcRun = UHCRun;
        this.teams = teams;

    }

    @Override
    public void run() {
        uhcRun.setState(GameState.FINISH);

        if (timer == 20 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage("§5[UHCRun] §6Arret dans §4" + timer + " §6secondes");
        }

        if (timer == 0){
            Bukkit.broadcastMessage("§5[UHCRun] §6Arret de la partie");
            for (Player player : uhcRun.getPlayers()){
                Location spawn = new Location(player.getWorld(), 0, 100, 0);
                uhcRun.beInsensible(player);
                player.teleport(spawn);
                player.getInventory().clear();
                player.setStatistic(Statistic.PLAYER_KILLS, 0);
                uhcRun.createLobbyBoard(player, teams);

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
