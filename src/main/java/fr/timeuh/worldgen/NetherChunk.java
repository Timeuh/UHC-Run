package fr.timeuh.worldgen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
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
            Location loc = source.getBlock(0,0,0).getLocation();
            paste(loc);
        }
    }

    private void paste(Location pasteLoc) {
        File file = new File("D:\\Bureau\\All\\PluginsMinecraft\\UHCRunV2\\src\\main\\resources\\Nether.schematic");
        EditSession session = new EditSession(new BukkitWorld(pasteLoc.getWorld()), 1000000);
        Vector loc = new Vector(pasteLoc.getX(), pasteLoc.getY(), pasteLoc.getZ());
        Bukkit.getScheduler().runTaskLater(
                uhcRun, new Runnable() {

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
                }, 0
            );

    }
}
