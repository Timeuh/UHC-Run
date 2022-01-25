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
    private static int timer = 30;

    public GameCycle(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @Override
    public void run() {
        WorldBorder border = Bukkit.getWorld("world").getWorldBorder();

        if (uhcRun.isState(GameState.PLAYING)){
            if (timer == 20 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
                Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §4" + timer +" §6secondes");
            }

            if (timer == 0){
                for (Player player : uhcRun.getAlivePlayers()){
                    int i = 0;
                    uhcRun.createPVPBoard(player);
                    uhcRun.ajouterNoFall(player);
                    player.teleport(uhcRun.getPvp().get(i));
                    uhcRun.checkWin(uhcRun);
                    i++;
                }
                cancel();
                Bukkit.broadcastMessage("§5[UHCRun] §6Teleportation finale");
                uhcRun.setState(GameState.FIGHTING);
            }
        }

        if (uhcRun.isState(GameState.FIGHTING)) {
            if (border.getSize() > 250) {
                border.setSize(250, 300);
                border.setDamageAmount(2);
                border.setDamageBuffer(2);
            }
        }
        timer--;
    }

    public static int getTimer(){
        return timer;
    }

    public static void resetTimer(){
        timer = 30;
    }
}
