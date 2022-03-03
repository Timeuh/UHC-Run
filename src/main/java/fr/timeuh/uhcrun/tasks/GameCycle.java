package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
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
            if (timer == 30 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
                Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §4" + timer +" §6secondes");
            }

            switch(timer){
                case 600:
                    Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §410 §6minutes");
                    break;

                case 300:
                    Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §45 §6minutes");
                    break;

                case 120:
                    Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §42 §6minutes");
                    break;

                case 60:
                    Bukkit.broadcastMessage("§5[UHCRun] §6Combat final dans §41 §6minute");
                    break;
            }

            if (timer == 0){
                int i = 0;
                for (Player player : uhcRun.getAlivePlayers()){
                    uhcRun.createPVPBoard(player);
                    uhcRun.addNoFall(player);
                    player.teleport(uhcRun.getPvp().get(i));
                    uhcRun.checkWin(uhcRun);
                    i++;
                }
                cancel();
                Bukkit.broadcastMessage("§5[UHCRun] §6Teleportation finale");
                uhcRun.setState(GameState.FIGHTING);
            }

            for (Player player : uhcRun.getAlivePlayers()){
                uhcRun.createBoard(player);
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
