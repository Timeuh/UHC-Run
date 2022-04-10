package fr.timeuh.uhcrun.scenarios;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HasteyBoys implements Listener {
    private UHCRun uhcRun;
    private final List<Material> enchantToolsDiamond;
    private final List<Material> enchantToolsGold;
    private final List<Material> enchantToolsIron;
    private final List<Material> enchantToolsStone;
    private final List<Material> enchantToolsWood;

    public HasteyBoys(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.enchantToolsDiamond = getEnchantToolsDiamond() ;
        this.enchantToolsGold = getEnchantToolsGold();
        this.enchantToolsIron = getEnchantToolsIron();
        this.enchantToolsStone = getEnchantToolsStone();
        this.enchantToolsWood = getEnchantToolsWood();
    }

    @EventHandler
    public void enchantTools(CraftItemEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.HASTEYBOYS)){
            ItemStack item = event.getRecipe().getResult();
            if (enchantToolsDiamond.contains(item.getType())){
                ItemMeta metaItem = item.getItemMeta();
                metaItem.addEnchant(Enchantment.DIG_SPEED, 3, false);
                metaItem.addEnchant(Enchantment.DURABILITY, 3, false);
                item.setItemMeta(metaItem);
                event.getInventory().setResult(item);
            } else if (enchantToolsGold.contains(item.getType())){
                ItemMeta metaItem = item.getItemMeta();
                metaItem.addEnchant(Enchantment.DIG_SPEED, 5, false);
                metaItem.addEnchant(Enchantment.DURABILITY, 5, true);
                item.setItemMeta(metaItem);
                event.getInventory().setResult(item);
            } else if (enchantToolsStone.contains(item.getType()) || enchantToolsWood.contains(item.getType()) || enchantToolsIron.contains(item.getType())){
                ItemMeta metaItem = item.getItemMeta();
                metaItem.addEnchant(Enchantment.DIG_SPEED, 2, false);
                metaItem.addEnchant(Enchantment.DURABILITY, 3, false);
                item.setItemMeta(metaItem);
                event.getInventory().setResult(item);
            }
        }
    }

    private List<Material> getEnchantToolsDiamond() {
        List<Material> diamondTools = new ArrayList<>();
        diamondTools.add(Material.DIAMOND_AXE);
        diamondTools.add(Material.DIAMOND_PICKAXE);
        diamondTools.add(Material.DIAMOND_SPADE);
        return diamondTools;
    }

    private List<Material> getEnchantToolsGold() {
        List<Material> goldTools = new ArrayList<>();
        goldTools.add(Material.GOLD_AXE);
        goldTools.add(Material.GOLD_PICKAXE);
        goldTools.add(Material.GOLD_SPADE);
        return goldTools;
    }

    private List<Material> getEnchantToolsIron() {
        List<Material> ironTools = new ArrayList<>();
        ironTools.add(Material.IRON_AXE);
        ironTools.add(Material.IRON_PICKAXE);
        ironTools.add(Material.IRON_SPADE);
        return ironTools;
    }

    private List<Material> getEnchantToolsStone() {
        List<Material> stoneTools = new ArrayList<>();
        stoneTools.add(Material.STONE_AXE);
        stoneTools.add(Material.STONE_PICKAXE);
        stoneTools.add(Material.STONE_SPADE);
        return stoneTools;
    }

    private List<Material> getEnchantToolsWood() {
        List<Material> woodenTools = new ArrayList<>();
        woodenTools.add(Material.WOOD_AXE);
        woodenTools.add(Material.WOOD_PICKAXE);
        woodenTools.add(Material.WOOD_SPADE);
        return woodenTools;
    }
}
