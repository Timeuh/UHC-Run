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

    public GameStop(UHCRun UHCRun){
        this.uhcRun = UHCRun;
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("Arrêt de la partie");
        uhcRun.setState(GameState.FINISH);

        for (int i = 0 ; i < uhcRun.getPlayers().size(); i++){
            Player player = uhcRun.getPlayers().get(i);
            Location spawn = new Location(player.getWorld(), 0, 100, 0);
            player.teleport(spawn);
            player.getInventory().clear();
        }
        cancel();
    }
}
