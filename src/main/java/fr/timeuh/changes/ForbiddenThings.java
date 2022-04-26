package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public class ForbiddenThings implements Listener {

    private UHCRun uhcRun;

    public ForbiddenThings(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event){
        event.getBlock().setData((byte) (event.getBlock().getData()%4));
        event.setCancelled(true);
    }

    @EventHandler
    public void noFire(EnchantItemEvent event){
        event.getEnchantsToAdd().remove(Enchantment.FIRE_ASPECT);
        event.getEnchantsToAdd().remove(Enchantment.ARROW_FIRE);
    }

    @EventHandler
    public void potionRestriction(BrewEvent event){
        for (ItemStack stack : event.getContents()){
            if (stack.isSimilar(new ItemStack(Material.GLOWSTONE_DUST))){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Les potions niveau deux sont " + ChatColor.DARK_RED + "désactivées");
                event.setCancelled(true);
            } else if (stack.isSimilar(new ItemStack(Material.BLAZE_POWDER))){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Les potions de force sont " + ChatColor.DARK_RED + "désactivées");
                event.setCancelled(true);
            }
        }
    }
}
