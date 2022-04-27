package fr.timeuh.worldgen;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SugarCaneGrowth extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk source) {
        for (int y = 20; y < 70; y++){
            for (int x = 0; x < 16; x++){
                for (int z = 0; z < 16; z++){
                    Block current = source.getBlock(x, y, z);
                    if (current.getType().equals(Material.SAND)){
                        if (waterNear(current) && airUp(current)){
                            createCane(current);
                        }
                    }
                }
            }
        }
    }

    private boolean waterNear(Block block){
        for (BlockFace face : getFaces()){
            if (block.getRelative(face).getType().equals(Material.STATIONARY_WATER)|| block.getRelative(BlockFace.UP).getType().equals(Material.WATER)){
                return true;
            }
        }
        return false;
    }

    private boolean airUp(Block block){
        return block.getRelative(BlockFace.UP).getType().equals(Material.AIR);
    }

    private void createCane(Block block){
        if (block.getType().equals(Material.SAND)){
            Random caneNumber = new Random();
            Random caneChance = new Random();

            if ((caneChance.nextInt(10)+1) > 9) {
                for (int y = 0; y <= caneNumber.nextInt(3); y++) {
                    block = block.getRelative(BlockFace.UP);
                    block.setType(Material.SUGAR_CANE_BLOCK);
                }
            }
        }
    }

    private List<BlockFace> getFaces(){
        List<BlockFace> allFaces = new ArrayList<>();
        allFaces.add(BlockFace.EAST);
        allFaces.add(BlockFace.WEST);
        allFaces.add(BlockFace.NORTH);
        allFaces.add(BlockFace.SOUTH);
        return allFaces;
    }

}
