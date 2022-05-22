package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
        if (block.getType().equals(Material.LOG) || block.getType().equals(Material.LOG_2)) {
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.LOG));
        }
    }

    @EventHandler
    public void transformBlocks(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType().equals(Material.SAND)){
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.GLASS));
        } else if (block.getType().equals(Material.GRAVEL)){
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.FLINT));
        } else if (block.getType().equals(Material.CACTUS)){
            while (!block.getRelative(BlockFace.DOWN).getType().equals(Material.SAND)){
                block = block.getRelative(BlockFace.DOWN);
            }
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.LOG));

            while (block.getRelative(BlockFace.UP).getType().equals(Material.CACTUS)){
                block = block.getRelative(BlockFace.UP);
                block.setType(Material.AIR);
                Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.LOG));
            }
        }
    }

    @EventHandler
    public void allCobble(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType().equals(Material.STONE) || block.getType().equals(Material.SANDSTONE)){
            block.setType(Material.AIR);
            Bukkit.getWorld("world").dropItem(block.getLocation(), new ItemStack(Material.COBBLESTONE));
        }
    }

    @EventHandler
    public void changeMobDrops(EntityDeathEvent event){
        Entity mob = event.getEntity();
        World world = event.getEntity().getWorld();
        ItemStack food = new ItemStack(Material.COOKED_BEEF, 2);
        ItemStack leather = new ItemStack(Material.LEATHER);
        ItemStack arrow = new ItemStack(Material.ARROW, 4);
        ItemStack string = new ItemStack(Material.STRING, 2);
        ItemStack feather = new ItemStack(Material.STRING, 2);
        ItemStack bow = new ItemStack(Material.BOW, 1, (short) 200);
        Random bowChance = new Random();

        if (mob instanceof Cow || mob instanceof Pig || mob instanceof Zombie){
            event.getDrops().removeAll(event.getDrops());
            world.dropItem(mob.getLocation(), food);
            world.dropItem(mob.getLocation(), leather);
        } else if (mob instanceof Skeleton){
            event.getDrops().removeAll(event.getDrops());
            world.dropItem(mob.getLocation(), food);
            if ((bowChance.nextInt(2)+1) == 2) world.dropItem(mob.getLocation(), bow);
        } else if (mob instanceof Chicken){
            event.getDrops().removeAll(event.getDrops());
            world.dropItem(mob.getLocation(), food);
            world.dropItem(mob.getLocation(), arrow);
            world.dropItem(mob.getLocation(), feather);
        } else if (mob instanceof Sheep){
            event.getDrops().removeAll(event.getDrops());
            world.dropItem(mob.getLocation(), food);
            world.dropItem(mob.getLocation(), leather);
            world.dropItem(mob.getLocation(), string);
        }
    }
}
