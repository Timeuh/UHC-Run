package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import fr.timeuh.scenario.Scenario;
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

import java.util.ArrayList;
import java.util.List;
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

            if (!(uhcRun.getScenario().isEnabled(Scenario.TIMBER))) {
                if (checkSurroundings(block) && lastLog(block)) {
                    String type = getTreeType(block);
                    if (type.equals("Thin")) {
                        uhcRun.getChop().breakLeaves(block);
                    } else if (type.equals("Big")) {
                        uhcRun.getChop().breakBigLeaves(block);
                    }
                }
            }
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

    private Block getTreeFoot(Block block){
        while (!(block.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT) || block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))){
            block = block.getRelative(BlockFace.DOWN);
        }
        return block;
    }

    private String getTreeType(Block block){
        Block foot = getTreeFoot(block);
        while  (!(foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2))){
            for (BlockFace face : getFaces()){
                if (foot.getRelative(face).getType().equals(foot.getType())) return "Big";
            }
            foot = foot.getRelative(BlockFace.UP);
        }
        return "Thin";
    }

    private List<BlockFace> getFaces(){
        List<BlockFace> allFaces = new ArrayList<>();
        allFaces.add(BlockFace.EAST);
        allFaces.add(BlockFace.EAST_NORTH_EAST);
        allFaces.add(BlockFace.EAST_SOUTH_EAST);
        allFaces.add(BlockFace.NORTH_EAST);
        allFaces.add(BlockFace.NORTH_NORTH_EAST);
        allFaces.add(BlockFace.SOUTH_EAST);
        allFaces.add(BlockFace.SOUTH_SOUTH_EAST);

        allFaces.add(BlockFace.WEST);
        allFaces.add(BlockFace.WEST_NORTH_WEST);
        allFaces.add(BlockFace.WEST_SOUTH_WEST);
        allFaces.add(BlockFace.NORTH_NORTH_WEST);
        allFaces.add(BlockFace.NORTH_WEST);
        allFaces.add(BlockFace.SOUTH_SOUTH_WEST);
        allFaces.add(BlockFace.SOUTH_WEST);

        allFaces.add(BlockFace.NORTH);
        allFaces.add(BlockFace.SOUTH);
        return allFaces;
    }
}
