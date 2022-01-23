package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCycle  extends BukkitRunnable {

    private UHCRun uhcRun;
    private int timer = 30;

    public GameCycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @Override
    public void run() {
        WorldBorder border = Bukkit.getWorld("world").getWorldBorder();

        if (timer == 20 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage("§3Combat final dans " + timer +" secondes");
        }

        if (timer == 0 && !uhcRun.isState(GameState.FINISH)){
            for (Player player : uhcRun.getAlivePlayers()){
                player.teleport(new Location(Bukkit.getWorld("world"),0,100,0,12f,17f));
            }
            cancel();
            Bukkit.broadcastMessage("Teleportation finale");
            uhcRun.setState(GameState.FIGHTING);
        }

        if (uhcRun.isState(GameState.FIGHTING)) {
            if (border.getSize() > 250) {
                border.setSize(250, 60);
                border.setDamageAmount(2);
                border.setDamageBuffer(2);
            }

            /*if (uhcRun.getAlivePlayers().size() == 0){
                if (uhcRun.getPlayers().size() > 0){
                    GameStop stop = new GameStop(uhcRun);
                    stop.runTaskTimer(uhcRun, 0, 20);
                }
            }*/
        }

        timer--;
    }
}
