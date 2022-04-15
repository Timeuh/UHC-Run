package fr.timeuh.game;

import org.bukkit.scheduler.BukkitRunnable;

public class Stop extends BukkitRunnable {
    @Override
    public void run() {
        cancel();
    }
}
