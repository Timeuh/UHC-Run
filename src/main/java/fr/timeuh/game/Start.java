package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable {
    private UHCRun uhcRun;
    private int timer;

    public Start(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.timer = 30;
    }

    @Override
    public void run() {
        uhcRun.setState(State.STARTING);
        switch (timer){
            case 0:
                uhcRun.setState(State.PLAYING);
                Cycle game = new Cycle(uhcRun);
                game.runTaskTimer(uhcRun, 0, 20);
                cancel();

            case 20:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "début de la partie dans " + ChatColor.DARK_RED + timer + ChatColor.GOLD + " secondes");
        }
        timer --;
    }
}
