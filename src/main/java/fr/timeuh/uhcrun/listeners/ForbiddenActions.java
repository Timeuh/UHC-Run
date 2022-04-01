package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
    public void onItemTransfer(InventoryMoveItemEvent event){
        if (event.getDestination().getName().equalsIgnoreCase("Brewing")){
            if (event.getItem().equals(new ItemStack(Material.GLOWSTONE))){
                List<HumanEntity> watchingPlayers = event.getSource().getHolder().getInventory().getViewers();
                for (HumanEntity player: watchingPlayers) {
                    player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "L'End est " + ChatColor.DARK_RED + "désactivé");
                }
            }
        }
    }
}
