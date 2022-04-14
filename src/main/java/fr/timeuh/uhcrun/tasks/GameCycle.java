package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GameCycle  extends BukkitRunnable {

    private UHCRun uhcRun;
    private static int gameTime = 1200;
    private static int timer = gameTime;

    public GameCycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @Override
    public void run() {
        WorldBorder border = Bukkit.getWorld("world").getWorldBorder();

        if (uhcRun.isState(GameState.PLAYING)) {
            if (timer == 30 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1) {
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
            }
            for (Player sbPlayer : uhcRun.getActualPlayers()) {
                uhcRun.createBoard(sbPlayer);
            }
        }
        switch (timer) {
            case 900:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Combat final dans " + ChatColor.DARK_RED + 15 + ChatColor.GOLD + " minutes");
                break;

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
                for (Player player : uhcRun.getActualAlivePlayers()) {
                    uhcRun.createPVPBoard(player);
                    player.teleport(uhcRun.getPvp().get(i));
                    uhcRun.beInsensible(player);
                    for (PotionEffect effect : player.getActivePotionEffects())
                        player.removePotionEffect(effect.getType());
                    i++;
                }
                uhcRun.checkWin(uhcRun, Bukkit.getPlayer(uhcRun.getAlivePlayers().get(0)));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Téléportation finale");
                uhcRun.setState(GameState.FIGHTING);
                break;
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
        timer = gameTime;
    }
}
