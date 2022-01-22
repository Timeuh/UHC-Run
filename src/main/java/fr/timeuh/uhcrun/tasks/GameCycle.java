package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCycle  extends BukkitRunnable {

    private UHCRun uhcRun;
    private int timer = 0;

    public GameCycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @Override
    public void run() {

        if (timer == 20 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage("§3Combat final dans : " + timer +" secondes");
        }

        if (timer == 30){
            for (Player player : uhcRun.getPlayers()){
                player.teleport(new Location(Bukkit.getWorld("world"),0,65,0,12f,17f));
            }
            cancel();
            Bukkit.broadcastMessage("Teleportation finale");
        }
        timer++;
    }
}
