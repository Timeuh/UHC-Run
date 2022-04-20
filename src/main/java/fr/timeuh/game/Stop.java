package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Stop extends BukkitRunnable {
    private UHCRun uhcRun;
    private int timer;

    public Stop(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.timer = 10;
    }

    @Override
    public void run() {
        switch (timer){
            case 0:
                uhcRun.setState(State.FINISH);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Fin de la partie");
                for (Player player : uhcRun.getPlayers().allCoPlayers()){
                    for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false), false);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().clear();
                    uhcRun.getBoard().displayLobby(player);
                }
                cancel();
                break;

            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Fin de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
                break;

            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Fin de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " seconde");
                break;
        }
        timer --;
    }
}
