package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStart extends BukkitRunnable {

    private int timer = 10;
    private UHCRun uhcRun;

    public GameStart(UHCRun UHCRun){
        this.uhcRun = UHCRun;
    }

    @Override
    public void run() {
        uhcRun.setState(GameState.STARTING);
        if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD +"Démarrage dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
        }

        if (timer == 0){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Démarrage de la partie");
            uhcRun.setState(GameState.PLAYING);

            for (Player alivePlayer : uhcRun.getPlayers()){
                uhcRun.getAlivePlayers().add(alivePlayer);
            }

            for (int i = 0 ; i < uhcRun.getAlivePlayers().size(); i++){
                Player player = uhcRun.getAlivePlayers().get(i);
                Location spawn = uhcRun.getSpawns().get(i);
                uhcRun.beInsensible(player);
                uhcRun.createBoard(player);
                player.teleport(spawn);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().setItem(0, new ItemStack(Material.COOKED_BEEF, 64));
                player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 4));
                player.updateInventory();
            }
            WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
            border.setCenter(0,0);
            border.setSize(3000);
            GameCycle.resetTimer();
            GameCycle cycle = new GameCycle(uhcRun);
            cycle.runTaskTimer(uhcRun,0,20);
            PlayerTeams.updateTeams(uhcRun);

            cancel();
        }

        timer --;
    }
}
