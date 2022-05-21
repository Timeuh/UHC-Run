package fr.timeuh.worldgen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import fr.timeuh.UHCRun;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.generator.BlockPopulator;


import java.io.File;
import java.io.IOException;
import java.util.Random;

public class NetherChunk extends BlockPopulator {
    private UHCRun uhcRun;

    public NetherChunk(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public void populate(World world, Random random, Chunk source){
        if ((source.getX() % 10 == 0) && (source.getZ() % 10 == 0)) {
            Location loc = source.getBlock(0,27,0).getLocation();
            paste(loc);
        }
    }

    private void paste(Location pasteLoc) {
        File file = new File(uhcRun.getDataFolder().getAbsolutePath()  + "/NetherChunk.schematic");
        EditSession session = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 1000000);
        Vector loc = new Vector(pasteLoc.getX(), pasteLoc.getY(), pasteLoc.getZ());
        Bukkit.getScheduler().runTaskLater(uhcRun, new Runnable() {
            @Override
            public void run() {
                try {
                    CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
                    cc.paste(session, loc, false);
                } catch (com.sk89q.worldedit.world.DataException | IOException | MaxChangedBlocksException e) {
                    System.out.print("DataException while loading schematic!");
                    e.printStackTrace();
                }
            }
            }, 0);
    }

}
