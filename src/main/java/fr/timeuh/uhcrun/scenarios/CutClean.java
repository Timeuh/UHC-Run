package fr.timeuh.uhcrun.scenarios;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CutClean implements Listener {
    private final List<Material> duplicateOres;
    private final List<Material> stoneToCobble;
    private UHCRun uhcRun;

    public CutClean(UHCRun uhcRun) {
        this.duplicateOres = getDuplicateOres();
        this.stoneToCobble = getCobblestoneStones();
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onOreBreak(BlockBreakEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.CUTCLEAN)){
            Block breakBlock = event.getBlock();
            World world = Bukkit.getWorld("world");
            if (duplicateOres.contains(breakBlock.getType())){
                dropOreDrops(world, breakBlock, breakBlock.getType());
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.CUTCLEAN)) {
            Block breakBlock = event.getBlock();
            if (stoneToCobble.contains(breakBlock.getType())){
                breakBlock.setType(Material.AIR);
                Bukkit.getWorld("world").dropItem(breakBlock.getLocation(), new ItemStack(Material.COBBLESTONE));
            } else if (breakBlock.getType().equals(Material.SAND)){
                breakBlock.setType(Material.AIR);
                Bukkit.getWorld("world").dropItem(breakBlock.getLocation(), new ItemStack(Material.GLASS));
            }
        }
    }

    private List<Material> getDuplicateOres(){
        List<Material> duplicateOres = new ArrayList<>();
        duplicateOres.add(Material.IRON_ORE);
        duplicateOres.add(Material.GOLD_ORE);
        duplicateOres.add(Material.EMERALD_ORE);
        duplicateOres.add(Material.DIAMOND_ORE);
        duplicateOres.add(Material.REDSTONE_ORE);
        duplicateOres.add(Material.LAPIS_ORE);
        duplicateOres.add(Material.COAL_ORE);
        return duplicateOres;
    }

    private List<Material> getCobblestoneStones(){
        List<Material> stoneToCobble = new ArrayList<>();
        stoneToCobble.add(Material.STONE);
        stoneToCobble.add(Material.SANDSTONE);
        stoneToCobble.add(Material.COBBLESTONE);
        return stoneToCobble;
    }

    private void dropOreDrops(World world, Block block, Material material){
        ItemStack items = new ItemStack(Material.AIR);
        switch (material){
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
        block.setType(Material.AIR);
        world.dropItemNaturally(block.getLocation(), items);
        world.spawn(block.getLocation(), ExperienceOrb.class).setExperience(dropOreExp(material));
    }

    private int dropOreExp(Material material){
        switch (material) {
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
        return 1;
    }
}
