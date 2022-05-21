package fr.timeuh.scenario;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Timber implements Listener {

    private UHCRun uhcRun;

    public Timber(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void dropApples(LeavesDecayEvent event){
        if (uhcRun.getScenario().isEnabled(Scenario.TIMBER)) {
            Location location = event.getBlock().getLocation();
            Random appleChance = new Random();

            event.getBlock().setType(Material.AIR);
            if (appleChance.nextInt(100) > 80) {
                Bukkit.getWorld("world").dropItem(location, new ItemStack(Material.APPLE));
            }

            if (appleChance.nextInt(100) > 98) {
                Bukkit.getWorld("world").dropItem(location, new ItemStack(Material.GOLDEN_APPLE));
            }
        }
    }
}
