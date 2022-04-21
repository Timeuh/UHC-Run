package fr.timeuh.scenario;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Cutclean implements Listener {

    private UHCRun uhcRun;
    private List<Material> ores;

    public Cutclean(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.ores = getOres();
    }

    @EventHandler
    public void duplicateOre(BlockBreakEvent event){
        if (uhcRun.getScenario().isEnabled(Scenario.CUTCLEAN)) {
            Material ore = event.getBlock().getType();
            for (Material possibleOres : ores){
                if (ore.equals(possibleOres)){
                    dropDuplicatedOres(event.getBlock(), ore);
                    event.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    private int getExpToDrop(Material ore){
        switch (ore){
            case EMERALD_ORE:
            case DIAMOND_ORE:
                return 14;
            case GOLD_ORE:
            case IRON_ORE:
                return 8;
            case REDSTONE_ORE:
            case LAPIS_ORE:
                return 10;
            case COAL_ORE:
                return 6;
        }
        return 0;
    }

    private List<Material> getOres(){
        List<Material> ores = new ArrayList<>();
        ores.add(Material.COAL_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.LAPIS_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.EMERALD_ORE);
        return ores;
    }

    private void dropDuplicatedOres(Block block, Material ore){
        ItemStack items = null;
        switch (ore){
            case DIAMOND_ORE:
                items = new ItemStack(Material.DIAMOND, 2);
                break;
            case EMERALD_ORE:
                items = new ItemStack(Material.EXP_BOTTLE, 8);
                break;
            case GOLD_ORE:
                items = new ItemStack(Material.GOLD_INGOT, 2);
                break;
            case IRON_ORE:
                items = new ItemStack(Material.IRON_INGOT, 2);
                break;
            case REDSTONE_ORE:
                items = new ItemStack(Material.REDSTONE, 8);
                break;
            case LAPIS_ORE:
                items = new ItemStack(Material.INK_SACK, 8, (short) 4);
                break;
            case COAL_ORE:
                items = new ItemStack(Material.TORCH, 4);
                break;
        }
        Bukkit.getWorld("world").dropItemNaturally(block.getLocation(), items);
        Bukkit.getWorld("world").spawn(block.getLocation(), ExperienceOrb.class).setExperience(getExpToDrop(ore));
    }

}
