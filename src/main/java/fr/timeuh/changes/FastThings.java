package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FastThings implements Listener {

    private UHCRun uhcRun;

    public FastThings(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void fastDecay(BlockBreakEvent event){
        Block block = event.getBlock();
        block.setType(Material.AIR);
        block.getState().update(true);

        dropApple(block);
        dropGoldenApple(block);
    }

    private void dropApple(Block block){
        Location loc = block.getLocation();
        Random appleChance = new Random();
        ItemStack resultDrop = null;

        if ((appleChance.nextInt(50)+1) >= 45) resultDrop = new ItemStack(Material.APPLE);
        if (resultDrop != null) Bukkit.getWorld("world").dropItem(loc,resultDrop);
    }

    private void dropGoldenApple(Block block){
        Location loc = block.getLocation();
        Random goldAppleChance = new Random();
        ItemStack resultDrop = null;

        if ((goldAppleChance.nextInt(100)+1) >= 95) resultDrop = new ItemStack(Material.GOLDEN_APPLE);
        if (resultDrop != null) Bukkit.getWorld("world").dropItem(loc,resultDrop);
    }
}
