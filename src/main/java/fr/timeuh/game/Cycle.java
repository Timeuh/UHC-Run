package fr.timeuh.game;

import org.bukkit.scheduler.BukkitRunnable;

public class Cycle extends BukkitRunnable {
    @Override
    public void run() {
        cancel();
    }
}
