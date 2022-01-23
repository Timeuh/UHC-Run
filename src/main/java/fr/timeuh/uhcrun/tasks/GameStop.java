package fr.timeuh.uhcrun.tasks;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStop extends BukkitRunnable {

    private UHCRun uhcRun;
    private int timer = 30;

    public GameStop(UHCRun UHCRun){
        this.uhcRun = UHCRun;
    }

    @Override
    public void run() {
        uhcRun.setState(GameState.FINISH);

        if (timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1){
            Bukkit.broadcastMessage("§6Arrêt dans §4" + timer + " §6secondes");
        }

        if (timer == 0){
            Bukkit.broadcastMessage("§6Arrêt de la partie");
            for (int i = 0 ; i < uhcRun.getPlayers().size(); i++){
                Player player = uhcRun.getPlayers().get(i);
                Location spawn = new Location(player.getWorld(), 0, 100, 0);
                player.teleport(spawn);
                player.getInventory().clear();
            }
            cancel();
        }
        timer--;
    }
}
