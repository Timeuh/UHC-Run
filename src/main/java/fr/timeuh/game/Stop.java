package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.*;
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
                World world = Bukkit.getWorld("world");
                Location tp = new Location(world, world.getSpawnLocation().getX(), 100, world.getSpawnLocation().getZ());
                for (Player player : uhcRun.getPlayers().allCoPlayers()){
                    if (player != null) {
                        for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                        player.teleport(tp);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false), false);
                        player.setGameMode(GameMode.ADVENTURE);
                        player.getInventory().clear();
                        player.updateInventory();
                        uhcRun.getBoard().displayLobby(player);
                    }
                }
                Bukkit.getWorld("world").getWorldBorder().setSize(1500);
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
