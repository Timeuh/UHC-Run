package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class ForbiddenThings implements Listener {

    private UHCRun uhcRun;

    public ForbiddenThings(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event){
        event.getBlock().setType(Material.AIR);
    }
}
