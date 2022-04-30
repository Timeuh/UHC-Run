package fr.timeuh.changes;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
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
    public void onCraft(CraftItemEvent event){
        if (event.getRecipe().getResult().isSimilar(new ItemStack(Material.FISHING_ROD))){
            event.getWhoClicked().sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La canne à pêche est " + ChatColor.DARK_RED + "désactivée");
            event.setCancelled(true);
        } else if (event.getRecipe().getResult().isSimilar(new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1))){
            event.getWhoClicked().sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La pomme de notch est " + ChatColor.DARK_RED + "désactivée");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        EntityRegainHealthEvent.RegainReason healReson = event.getRegainReason();
        if (healReson.equals(EntityRegainHealthEvent.RegainReason.SATIATED)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)){
            event.setCancelled(true);
        }
    }
}
