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
            Chunk bas = world.getChunkAt(0, -1);
            Chunk droite = world.getChunkAt(-1, 0);
            Chunk diagonale = world.getChunkAt(-1, -1);

            generateFloor(source);
            generateFloor(bas);
            generateFloor(droite);
            generateFloor(diagonale);

            generateWall(source, "source");
            generateWall(bas, "bas");
            generateWall(droite, "droite");
            generateWall(diagonale, "diagonale");
        }
    }

    private void generateFloor(Chunk source){
        for (int x = 0; x < 16; x++){
            for (int z = 0; z < 16; z++){
                source.getBlock(x, 99, z).setType(Material.GLASS);
            }
        }
    }

    private void generateWall(Chunk source, String direction){
        int xSource =0;
        int zSource =0;

        switch (direction){
            case "source":
                xSource = 15;
                zSource = 15;
                break;
            case "bas":
                xSource = 15;
                break;
            case "droite":
                zSource = 15;
                break;
            default: break;
        }

        for (int y = 100; y < 102; y ++){
            for (int x = 0; x < 16; x ++){
                for (int z = 0; z < 16; z ++){
                    if (x == xSource){
                        source.getBlock(x, y, z).setType(Material.STAINED_GLASS_PANE);
                    }
                    if (z == zSource){
                        source.getBlock(x, y, z).setType(Material.STAINED_GLASS_PANE);
                    }
                }
            }
        }

    }
}
