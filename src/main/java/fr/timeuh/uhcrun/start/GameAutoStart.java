package fr.timeuh.uhcrun.start;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameAutoStart extends BukkitRunnable {

    private int timer = 10;;
    private UHCRun UHCRun;

    public GameAutoStart(UHCRun UHCRun){
        this.UHCRun = UHCRun;
    }

    @Override
    public void run() {

        for (Player pls : UHCRun.getPlayers()){
            pls.setLevel(timer);
        }

        if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage("§6Démarrage dans : §4" + timer + " §6secondes");
        }

        if (timer == 0){
            Bukkit.broadcastMessage("Démarrage de la partie");
            UHCRun.setState(GameState.PLAYING);
            cancel();
        }

        timer --;
    }
}
