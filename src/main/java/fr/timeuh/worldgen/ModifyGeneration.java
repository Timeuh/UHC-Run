package fr.timeuh.worldgen;

import fr.timeuh.UHCRun;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class ModifyGeneration implements Listener {
    private UHCRun uhcRun;

    public ModifyGeneration(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void modifyGeneration(WorldInitEvent event){
        World world = event.getWorld();
        world.getPopulators().add(new SpawnPlatform());
        world.getPopulators().add(new SugarCaneGrowth());
        world.getPopulators().add(new AugmentOres());
    }
}
