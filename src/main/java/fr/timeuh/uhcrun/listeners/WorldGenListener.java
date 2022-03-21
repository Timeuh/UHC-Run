package fr.timeuh.uhcrun.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenListener implements Listener {
    private List<Material> materialList;

    public WorldGenListener() {
        this.materialList = createMaterialList();
    }

    @EventHandler
    public void modifyOreGeneration(WorldInitEvent event){
        World world = event.getWorld();
        BlockPopulator goldOreModifier = createCustomOrePopulator(60,9, Material.GOLD_ORE);
        BlockPopulator ironOreModifier = createCustomOrePopulator(60,9, Material.IRON_ORE);
        BlockPopulator diamondOreModifier = createCustomOrePopulator(50,9, Material.DIAMOND_ORE);
        world.getPopulators().add(goldOreModifier);
        world.getPopulators().add(ironOreModifier);
        world.getPopulators().add(diamondOreModifier);
    }

    public BlockPopulator createCustomOrePopulator(int maxLayer, int minChance, Material oreMaterial) {
        return new BlockPopulator() {
            @Override
            public void populate(World world, Random random, Chunk source) {
                int chance, xLayer, zLayer, oreChance;
                for (int yLayer = 1; yLayer < maxLayer; yLayer += 2) {
                    chance = random.nextInt(10) + 1;
                    if (chance >= minChance) {
                        xLayer = random.nextInt(16) + 1;
                        zLayer = random.nextInt(16) + 1;

                        for (Material material : materialList) {
                            if (source.getBlock(xLayer, yLayer, zLayer).getType().equals(material)) {
                                oreChance = random.nextInt(5);
                                switch (oreChance) {
                                    case 4:
                                        source.getBlock(xLayer, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer + 1, zLayer + 1).setType(oreMaterial, false);
                                        break;

                                    case 3:
                                        source.getBlock(xLayer, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer + 1).setType(oreMaterial, false);
                                        break;

                                    case 2:
                                        source.getBlock(xLayer, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        break;

                                    case 1:
                                        source.getBlock(xLayer, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer + 1, zLayer).setType(oreMaterial, false);
                                        break;

                                    case 0:
                                        source.getBlock(xLayer, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer).setType(oreMaterial, false);
                                        source.getBlock(xLayer, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        source.getBlock(xLayer + 1, yLayer, zLayer + 1).setType(oreMaterial, false);
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public List<Material> createMaterialList() {
        List<Material> list = new ArrayList<>();
        list.add(Material.STONE);
        list.add(Material.SANDSTONE);
        list.add(Material.OBSIDIAN);
        return list;
    }
}


