package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TreeChop {

    private UHCRun uhcRun;

    public TreeChop(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
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

    public void breakLeaves(Block block) {
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

    public void breakBigLeaves(Block block) {
        World world = block.getWorld();
        new BukkitRunnable() {
            int maxX = block.getX()+3, maxY = block.getY()+3, maxZ = block.getZ()+3;
            int x = block.getX()-3, y = block.getY()-3, z = block.getZ()-3;

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
                    x = block.getX()-3;
                    z ++;
                }
                if (z > maxZ){
                    x = block.getX()-3;
                    z = block.getZ()-3;
                    y ++;
                }
                if (y > maxY) cancel();
            }
        }.runTaskTimer(uhcRun,0,1);
    }
}
