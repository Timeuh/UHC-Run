package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.scheduler.BukkitRunnable;

public class Cycle extends BukkitRunnable {
    private UHCRun uhcRun;

    public Cycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public void run() {
        cancel();
    }
}
