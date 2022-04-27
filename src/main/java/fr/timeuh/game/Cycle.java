package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class Cycle extends BukkitRunnable {
    private UHCRun uhcRun;
    private int timer;

    public Cycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.timer = 40;
    }

    @Override
    public void run() {
        switch (timer){
            case 0:
                uhcRun.setState(State.PVP);
                for (Player player : uhcRun.getPlayers().allLivePlayers()){
                    if (player != null) {
                        for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                        uhcRun.getPlayers().beInvincible(player);
                    }
                }
                for (Player sbPlayer : uhcRun.getPlayers().allCoPlayers()) uhcRun.getBoard().displayPvp(sbPlayer);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Début de la phase PVP");
                uhcRun.getPlayers().teleportPlayers();
                uhcRun.getWins().checkWin();
                if (Bukkit.getWorld("world").getWorldBorder().getSize() > 250) Bukkit.getWorld("world").getWorldBorder().setSize(250, 600);
                cancel();
                break;

            case 30:
            case 20:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Phase PVP dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
                break;

            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Phase PVP dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " seconde");
                break;
        }
        if (timer > 0){
            for (Player player : uhcRun.getPlayers().allCoPlayers()) uhcRun.getBoard().displayRun(player, timer);
        }
        timer --;
    }
}
