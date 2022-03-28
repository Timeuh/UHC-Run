package fr.timeuh.uhcrun.worldgeneration;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class NetherChunk extends BlockPopulator {
    private UHCRun uhcRun;

    public NetherChunk(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @Override
    public void populate(World world, Random random, Chunk source) {
        int yRange = 30;
        int xRange = 16;
        int zRange = 16;

        if (source.getX() % 10 == 0 && source.getZ() % 10 == 0) {
            for (int yLayer = 0; yLayer <= yRange; yLayer ++){
                for (int zLayer = 0; zLayer < zRange; zLayer ++){
                    for (int xLayer = 0; xLayer < xRange; xLayer ++){
                        source.getBlock(xLayer, yLayer, zLayer).setType(chooseBlockMaterial(xLayer, yLayer, zLayer));
                    }
                }
            }
        }
    }

    private Material chooseBlockMaterial(int x, int y, int z){
        if (y == 1 || y == 2 || y == 29 || y == 30){
            return Material.NETHERRACK;
        } else if (y == 3 || y == 4){
            return Material.STATIONARY_LAVA;
        } else  if (y == 0){
            return Material.BEDROCK;
        } else {
            if ((x == 0 || x == 1 || x == 14 || x == 15) || (z == 0 || z == 1 || z == 14 || z == 15)){
                return Material.NETHERRACK;
            }
            if (y == 5 || y == 6 || y == 7 || y == 8 || y == 9 || y == 10 || y == 11 || y == 12 || y == 13 || y == 14 || y == 15 || y == 16 || y == 17 || y == 18){
                if ((x == 6 || x == 7 || x == 8 || x == 9) && (z == 6 || z == 7 || z == 8 || z == 9)){
                    return Material.NETHER_BRICK;
                }
            }
            if (y == 19){
                if ((x == 6 || x == 7 || x == 8 || x == 9) && (z >= 2 && z <= 15)){
                    return Material.NETHER_BRICK;
                }
                if ((x >= 2 && x <= 15) && (z == 6 || z == 7 || z == 8 || z == 9)){
                    return Material.NETHER_BRICK;
                }
            }
            if (y == 20){
                if ((x == 6 || x == 9) && ((z >= 2 && z <= 6) || (z >= 9 && z <= 15))){
                    return Material.NETHER_FENCE;
                }
                if (((x >= 2 && x <= 6) || (x >= 9 && x <= 15)) && (z == 6 || z == 9)){
                    return Material.NETHER_FENCE;
                }
            }
        }
        return Material.AIR;
    }
}
