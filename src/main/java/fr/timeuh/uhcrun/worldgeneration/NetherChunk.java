package fr.timeuh.uhcrun.worldgeneration;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

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
        boolean chest1Filled = false;
        boolean chest2Filled = false;
        boolean chest3Filled = false;
        boolean chest4Filled = false;
        Random slot = new Random();

        if (source.getX() % 10 == 0 && source.getZ() % 10 == 0) {
            for (int yLayer = 0; yLayer <= yRange; yLayer ++){
                for (int zLayer = 0; zLayer < zRange; zLayer ++){
                    for (int xLayer = 0; xLayer < xRange; xLayer ++){
                        source.getBlock(xLayer, yLayer, zLayer).setType(chooseBlockMaterial(xLayer, yLayer, zLayer));
                        BlockState state = source.getBlock(xLayer, yLayer, zLayer).getState();
                        if (state instanceof CreatureSpawner) {
                            ((CreatureSpawner) state).setSpawnedType(EntityType.BLAZE);
                            ((CreatureSpawner) state).setDelay(200);
                        } else if (state instanceof Chest) {
                            Inventory chest = ((Chest) state).getInventory();
                            if (!chest1Filled) {
                                chest.setItem(slot.nextInt(27), getChestItem(1, 1));
                                chest.setItem(slot.nextInt(27), getChestItem(1, 2));
                                chest.setItem(slot.nextInt(27), getChestItem(1, 3));
                                chest.setItem(slot.nextInt(27), getChestItem(1, 4));
                                chest.setItem(slot.nextInt(27), getChestItem(1, 5));
                                chest.setItem(slot.nextInt(27), getChestItem(1, 6));
                                chest1Filled = true;
                            } else if (!chest2Filled) {
                                chest.setItem(slot.nextInt(27), getChestItem(2, 1));
                                chest.setItem(slot.nextInt(27), getChestItem(2, 2));
                                chest.setItem(slot.nextInt(27), getChestItem(2, 3));
                                chest.setItem(slot.nextInt(27), getChestItem(2, 4));
                                chest.setItem(slot.nextInt(27), getChestItem(2, 5));
                                chest.setItem(slot.nextInt(27), getChestItem(2, 6));
                                chest2Filled = true;
                            } else if (!chest3Filled) {
                                chest.setItem(slot.nextInt(27), getChestItem(3, 1));
                                chest.setItem(slot.nextInt(27), getChestItem(3, 2));
                                chest.setItem(slot.nextInt(27), getChestItem(3, 3));
                                chest.setItem(slot.nextInt(27), getChestItem(3, 4));
                                chest.setItem(slot.nextInt(27), getChestItem(3, 5));
                                chest.setItem(slot.nextInt(27), getChestItem(3, 6));
                                chest3Filled = true;
                            } else if (!chest4Filled) {
                                chest.setItem(slot.nextInt(27), getChestItem(4, 1));
                                chest.setItem(slot.nextInt(27), getChestItem(4, 2));
                                chest.setItem(slot.nextInt(27), getChestItem(4, 3));
                                chest.setItem(slot.nextInt(27), getChestItem(4, 4));
                                chest.setItem(slot.nextInt(27), getChestItem(4, 5));
                                chest.setItem(slot.nextInt(27), getChestItem(4, 6));
                                chest4Filled = true;
                            }
                        }
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
                if (x == 8 && z == 8){
                    return Material.MOB_SPAWNER;
                }
                if ((x == 3 && z == 8) || (x == 12 && z == 7) || (x == 8 && z == 3) || (x == 7 && z == 12)){
                    return Material.CHEST;
                }
            }
        }
        return Material.AIR;
    }

    private ItemStack getChestItem(int chestId, int slotId){
        Random itemCount = new Random();
        Random enchantLevel = new Random();
        if (chestId == 1 || chestId == 2){
            switch (slotId){
                case 1:
                    return new ItemStack(Material.NETHER_WARTS, itemCount.nextInt(5) +1);
                case 2:
                    return new ItemStack(Material.GOLD_INGOT, itemCount.nextInt(8) +1);
                case 3:
                    return new ItemStack(Material.DIAMOND, itemCount.nextInt(3) +1);
                case 4:
                    return new ItemStack(Material.SUGAR, itemCount.nextInt(2) +1);
                case 5:
                    return new ItemStack(Material.REDSTONE, itemCount.nextInt(5) +1);
                case 6:
                    return new ItemStack(Material.SPIDER_EYE, itemCount.nextInt(2) +1);
                default: break;
            }
        } else if (chestId == 3 || chestId == 4){
            switch (slotId){
                case 1:
                    ItemStack enchantedBook1 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta metaBook1 = (EnchantmentStorageMeta) enchantedBook1.getItemMeta();
                    metaBook1.addStoredEnchant(getBookEnchantment(), enchantLevel.nextInt(2) +1, false);
                    enchantedBook1.setItemMeta(metaBook1);
                    return enchantedBook1;
                case 2:
                    ItemStack enchantedBook2 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta metaBook2 = (EnchantmentStorageMeta) enchantedBook2.getItemMeta();
                    metaBook2.addStoredEnchant(getBookEnchantment(), enchantLevel.nextInt(2) +1, false);
                    enchantedBook2.setItemMeta(metaBook2);
                    return enchantedBook2;
                case 3:
                    ItemStack enchantedBook3 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta metaBook3 = (EnchantmentStorageMeta) enchantedBook3.getItemMeta();
                    metaBook3.addStoredEnchant(getBookEnchantment(), enchantLevel.nextInt(2) +1, false);
                    enchantedBook3.setItemMeta(metaBook3);
                    return enchantedBook3;
                case 4:
                    ItemStack enchantedBook4 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta metaBook4 = (EnchantmentStorageMeta) enchantedBook4.getItemMeta();
                    metaBook4.addStoredEnchant(getBookEnchantment(), enchantLevel.nextInt(2) +1, false);
                    enchantedBook4.setItemMeta(metaBook4);
                    return enchantedBook4;
                case 5:
                    return new ItemStack(Material.GLASS_BOTTLE, itemCount.nextInt(3) +1);
                case 6:
                    return new ItemStack(Material.EXP_BOTTLE, itemCount.nextInt(8) +1);
                default: break;
            }
        }
        return new ItemStack(Material.NETHER_WARTS);
    }

    private Enchantment getBookEnchantment(){
        int enchantId = new Random().nextInt(30)+1;
        if (enchantId <= 10) return Enchantment.DAMAGE_ALL;
        if (enchantId <= 20) return Enchantment.PROTECTION_ENVIRONMENTAL;
        if (enchantId == 29) return Enchantment.ARROW_FIRE;
        if (enchantId == 30) return Enchantment.FIRE_ASPECT;
        return Enchantment.KNOCKBACK;
    }
}
