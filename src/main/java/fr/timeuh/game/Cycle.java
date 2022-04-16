package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "début de la phase PVP");
                cancel();
                break;

            case 30:
            case 20:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "phase PVP dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
                break;

            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "phase PVP dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " seconde");
                break;
        }
        timer --;
    }
}
