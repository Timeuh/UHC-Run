package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable {
    private UHCRun uhcRun;
    private int timer;

    public Start(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.timer = 10;
    }

    @Override
    public void run() {
        uhcRun.setState(State.STARTING);
        switch (timer){
            case 0:
                uhcRun.setState(State.PLAYING);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Début de la partie");
                for (Player player : uhcRun.getPlayers().allCoPlayers()){
                    uhcRun.getPlayers().beInvincible(player);
                    uhcRun.getPlayers().addLivePlayer(player.getUniqueId());
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false), false);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.getInventory().clear();
                    player.updateInventory();
                    uhcRun.getTeams().updateTeams(player);
                }
                Cycle game = new Cycle(uhcRun);
                game.runTaskTimer(uhcRun, 0, 20);
                cancel();
                break;

            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Début de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
                break;

            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Début de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " seconde");
                break;
        }
        timer --;
    }
}
