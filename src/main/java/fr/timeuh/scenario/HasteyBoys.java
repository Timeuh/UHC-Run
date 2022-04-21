package fr.timeuh.scenario;

import fr.timeuh.UHCRun;
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
    private List<ItemStack> wood;
    private List<ItemStack> iron;
    private List<ItemStack> gold;
    private List<ItemStack> diamond;

    public HasteyBoys(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.wood = woodenTools();
        this.iron = ironTools();
        this.gold = goldenTools();
        this.diamond = diamondTools();
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        ItemStack item = event.getRecipe().getResult();
        ItemStack result = null;
        if (uhcRun.getScenario().isEnabled(Scenario.HASTEYBOYS)) {
            if (item.isSimilar(wood.get(0))) {
                item = new ItemStack(Material.STONE_PICKAXE);
                result = enchantItem(item);
            } else if (item.isSimilar(wood.get(1))) {
                item = new ItemStack(Material.STONE_AXE);
                result = enchantItem(item);
            } else if (item.isSimilar(wood.get(2))) {
                item = new ItemStack(Material.STONE_SPADE);
                result = enchantItem(item);
            }

            for (ItemStack ironItem : iron){
                if (ironItem.isSimilar(item)) result = enchantItem(item);
            }
            for (ItemStack goldItem : gold){
                if (goldItem.isSimilar(item)) result = enchantItem(item);
            }
            for (ItemStack diamondItem : diamond){
                if (diamondItem.isSimilar(item)) result = enchantItem(item);
            }

            if (result != null) event.getInventory().setResult(result);
        }
    }

    private List<ItemStack> woodenTools(){
        List<ItemStack> tools = new ArrayList<>();
        ItemStack pickaxe = new ItemStack(Material.WOOD_PICKAXE);
        ItemStack shovel = new ItemStack(Material.WOOD_AXE);
        ItemStack axe = new ItemStack(Material.WOOD_SPADE);
        tools.add(pickaxe);
        tools.add(shovel);
        tools.add(axe);
        return tools;
    }

    private List<ItemStack> ironTools(){
        List<ItemStack> tools = new ArrayList<>();
        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
        ItemStack shovel = new ItemStack(Material.IRON_AXE);
        ItemStack axe = new ItemStack(Material.IRON_SPADE);
        tools.add(pickaxe);
        tools.add(shovel);
        tools.add(axe);
        return tools;
    }

    private List<ItemStack> goldenTools(){
        List<ItemStack> tools = new ArrayList<>();
        ItemStack pickaxe = new ItemStack(Material.GOLD_PICKAXE);
        ItemStack shovel = new ItemStack(Material.GOLD_AXE);
        ItemStack axe = new ItemStack(Material.GOLD_SPADE);
        tools.add(pickaxe);
        tools.add(shovel);
        tools.add(axe);
        return tools;
    }

    private List<ItemStack> diamondTools(){
        List<ItemStack> tools = new ArrayList<>();
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack shovel = new ItemStack(Material.DIAMOND_AXE);
        ItemStack axe = new ItemStack(Material.DIAMOND_SPADE);
        tools.add(pickaxe);
        tools.add(shovel);
        tools.add(axe);
        return tools;
    }

    private ItemStack enchantItem(ItemStack item){
        ItemMeta metaItem = item.getItemMeta();
        metaItem.addEnchant(Enchantment.DIG_SPEED, 3, false);
        metaItem.addEnchant(Enchantment.DURABILITY, 3, false);
        item.setItemMeta(metaItem);
        return item;
    }
}
