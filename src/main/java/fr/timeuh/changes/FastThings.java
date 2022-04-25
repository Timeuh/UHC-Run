package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FastThings implements Listener {

    private UHCRun uhcRun;

    public FastThings(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void fastDecay(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2)) {
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.LOG));

            if (checkSurroundings(block) && lastLog(block)) breakLeaves(block);
        }
    }

    private void dropApples(Block block){
        Location loc = block.getLocation();
        Random appleChance = new Random();
        Random goldAppleChance = new Random();
        ItemStack resultApple = null;
        ItemStack resultGoldenApple = null;

        if ((appleChance.nextInt(50)+1) >= 45) resultApple = new ItemStack(Material.APPLE);
        if ((goldAppleChance.nextInt(100)+1) == 100) resultGoldenApple = new ItemStack(Material.GOLDEN_APPLE);
        if (resultApple != null) Bukkit.getWorld("world").dropItem(loc,resultApple);
        if (resultGoldenApple != null) Bukkit.getWorld("world").dropItem(loc,resultGoldenApple);

    }

    private boolean checkSurroundings(Block block){
        for (int y = block.getY()-1; y < block.getY()+2; y++){
            for (int x = block.getX()-1; x < block.getX()+2; x++){
                for (int z = block.getZ()-1; z < block.getZ()+2; z++){
                    if (Bukkit.getWorld("world").getBlockAt(x, y, z).getType().equals(Material.LOG) || Bukkit.getWorld("world").getBlockAt(x, y, z).getType().equals(Material.LOG_2)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean lastLog(Block block){
        if (block.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || block.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2)){
            if (block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)){
                return true;
            }
        }
        return false;
    }

    private void breakLeaves(Block block) {
        World world = block.getWorld();
        new BukkitRunnable() {
            int maxX = block.getX()+2, maxY = block.getY()+2, maxZ = block.getZ()+2;
            int x = block.getX()-2, y = block.getY()-2, z = block.getZ()-2;

            @Override
            public void run() {
                Material blockType = world.getBlockAt(x, y, z).getType();
                Block current = world.getBlockAt(x, y, z);
                if (blockType.equals(Material.LEAVES) || blockType.equals(Material.LEAVES_2)){
                    current.setType(Material.AIR);
                    dropApples(current);
                }

                x ++;
                if (x > maxX){
                    x = block.getX()-2;
                    z ++;
                }
                if (z > maxZ){
                    x = block.getX()-2;
                    z = block.getZ()-2;
                    y ++;
                }
                if (y > maxY) cancel();
            }
        }.runTaskTimer(uhcRun,0,1);
    }
}
