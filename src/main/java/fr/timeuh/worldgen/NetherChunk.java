package fr.timeuh.worldgen;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class NetherChunk extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk source){
        if ((source.getX() % 10 == 0) && (source.getZ() % 10 == 0)){
            File nether = new File("src/main/resources/Nether.schematic");
            EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);
            try {
                CuboidClipboard clipboard = MCEditSchematicFormat.getFormat(nether).load(nether);
                clipboard.paste(session, new Vector(source.getX(), 0 ,source.getZ()), false);
            } catch (IOException | DataException | MaxChangedBlocksException e){
                e.printStackTrace();
            }
        }
    }
}
