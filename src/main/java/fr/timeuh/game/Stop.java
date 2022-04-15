package fr.timeuh.game;

import fr.timeuh.UHCRun;
import org.bukkit.scheduler.BukkitRunnable;

public class Stop extends BukkitRunnable {
    private UHCRun uhcRun;

    public Stop(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public void run() {
        uhcRun.setState(State.FINISH);
        cancel();
    }
}
