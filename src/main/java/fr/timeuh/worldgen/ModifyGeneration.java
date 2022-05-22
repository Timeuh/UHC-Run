package fr.timeuh.worldgen;

import fr.timeuh.UHCRun;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.lang.reflect.Field;

public class ModifyGeneration implements Listener {
    private UHCRun uhcRun;

    public ModifyGeneration(UHCRun uhcRun) {
        this.uhcRun = uhcRun;

        try {
            Field biomesField = BiomeBase.class.getDeclaredField("biomes");
            biomesField.setAccessible(true);

            if (biomesField.get(null) instanceof BiomeBase[]){
                BiomeBase[] biomes = (BiomeBase[]) biomesField.get(null);
                biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.OCEAN.id] = BiomeBase.FOREST;
                biomesField.set(null, biomes);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void modifyGeneration(WorldInitEvent event){
        World world = event.getWorld();
        world.getPopulators().add(new SpawnPlatform());
        world.getPopulators().add(new SugarCaneGrowth());
        world.getPopulators().add(new AugmentOres());
        world.getPopulators().add(new NetherChunk(uhcRun));
    }
}
