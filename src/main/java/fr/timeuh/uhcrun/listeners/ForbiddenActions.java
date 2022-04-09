package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class ForbiddenActions implements Listener {
    private UHCRun uhcRun;

    public ForbiddenActions(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le Nether est " + ChatColor.DARK_RED + "désactivé");
            event.setCancelled(true);
        } else if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "L'End est " + ChatColor.DARK_RED + "désactivé");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBrew(BrewEvent event){
        for (ItemStack stack : event.getContents()){
            if (stack.isSimilar(new ItemStack(Material.GLOWSTONE_DUST))){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Les potions niveau deux sont " + ChatColor.DARK_RED + "désactivées");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if (event.getRecipe().getResult().isSimilar(new ItemStack(Material.FISHING_ROD))){
            event.getWhoClicked().sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La canne à pêche est " + ChatColor.DARK_RED + "désactivée");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchantment(EnchantItemEvent event){
        event.getEnchantsToAdd().remove(Enchantment.FIRE_ASPECT);
        event.getEnchantsToAdd().remove(Enchantment.ARROW_FIRE);
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        EntityRegainHealthEvent.RegainReason healReson = event.getRegainReason();
        if (healReson.equals(EntityRegainHealthEvent.RegainReason.SATIATED)){
            event.setCancelled(true);
        }
    }
}
