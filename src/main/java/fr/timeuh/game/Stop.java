package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Stop extends BukkitRunnable {
    private UHCRun uhcRun;
    private int timer;

    public Stop(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.timer = 30;
    }

    @Override
    public void run() {
        switch (timer){
            case 0:
                uhcRun.setState(State.FINISH);
                cancel();

            case 20:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "fin de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
        }
        timer --;
    }
}
