package fr.timeuh.changes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeChop {

    public void dropApples(Block block) {
        Location loc = block.getLocation();
        Random appleChance = new Random();

        if ((appleChance.nextInt(50)) > 44) Bukkit.getWorld("world").dropItem(loc, new ItemStack(Material.APPLE));
        if ((appleChance.nextInt(200)) == 199) Bukkit.getWorld("world").dropItem(loc, new ItemStack(Material.GOLDEN_APPLE));
    }

    public void breakLeaves(Block block, int blockReach) {
        World world = block.getWorld();

        int maxX = block.getX() + blockReach, maxY = block.getY() + blockReach, maxZ = block.getZ() + blockReach;
        int x = block.getX() - blockReach, y = block.getY() - blockReach, z = block.getZ() - blockReach;

        for (int newY = y; newY <= maxY; newY ++){
            for (int newX = x; newX <= maxX; newX++){
                for (int newZ = z; newZ <= maxZ; newZ++){
                    Material blockType = world.getBlockAt(newX, newY, newZ).getType();
                    Block current = world.getBlockAt(newX, newY, newZ);
                    if (blockType.equals(Material.LEAVES) || blockType.equals(Material.LEAVES_2)) {
                        current.setType(Material.AIR);
                        dropApples(current);
                    }
                }
            }
        }
    }

    public String getTreeType(Block block){
        Block foot = getTreeFoot(block);
        while  (!(foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES) || foot.getRelative(BlockFace.UP).getType().equals(Material.LEAVES_2))){
            for (BlockFace face : getFaces()){
                if (foot.getRelative(face).getType().equals(foot.getType())) return "Big";
            }
            foot = foot.getRelative(BlockFace.UP);
        }
        return "Thin";
    }

    public Block getTreeFoot(Block block){
        while (!(block.getRelative(BlockFace.DOWN).getType().equals(Material.DIRT) || block.getRelative(BlockFace.DOWN).getType().equals(Material.GRASS))){
            block = block.getRelative(BlockFace.DOWN);
        }
        return block;
    }

    public List<BlockFace> getFaces(){
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
