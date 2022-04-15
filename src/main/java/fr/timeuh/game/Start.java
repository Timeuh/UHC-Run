package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable {
    UHCRun uhcRun;

    public Start(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public void run() {
        Cycle game = new Cycle(uhcRun);
        game.runTaskTimer(uhcRun, 0, 20);
        cancel();
    }
}
