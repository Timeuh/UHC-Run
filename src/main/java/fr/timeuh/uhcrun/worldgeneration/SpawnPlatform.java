package fr.timeuh.uhcrun.worldgeneration;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class SpawnPlatform extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk source) {
        if (source.getX() == 0 && source.getZ() == 0){
            Chunk droite = world.getChunkAt(0, -1);
            Chunk bas = world.getChunkAt(-1, 0);
            Chunk diagonale = world.getChunkAt(-1, -1);

            generateFloor(source);
            generateFloor(droite);
            generateFloor(bas);
            generateFloor(diagonale);
        }
    }

    private void generateFloor(Chunk source){
        for (int x = 0; x < 16; x++){
            for (int z = 0; z < 16; z++){
                source.getBlock(x, 99, z).setType(Material.GLASS);
            }
        }
    }
}
