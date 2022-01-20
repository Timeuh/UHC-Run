package com.timeuh.dsuhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class DSUHCListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();

        ItemStack selectionEquipe = new ItemStack(Material.STICK);
        ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();

        selectionEquipeMeta.setDisplayName("Sélection de l'équipe");
        selectionEquipe.setItemMeta(selectionEquipeMeta);

        player.getInventory().setItem(4, selectionEquipe);
        player.updateInventory();
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (item.getType() == Material.DIAMOND_SWORD){
            if (action == Action.RIGHT_CLICK_AIR){
                player.sendMessage("Frappe moi !");
            }
        }

        if (item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equalsIgnoreCase("Sélection de l'équipe")) {
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, "§6Menu Sélection d'équipe");
                inv.setItem(4, new ItemStack(Material.WOOL));
                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        if (current == null) return;

        if (inv.getName().equalsIgnoreCase("§6Menu Sélection d'équipe")){
            event.setCancelled(true);
            if (current.getType()== Material.WOOL){
                player.getInventory().setItem(0,new ItemStack(Material.DIAMOND));
                player.closeInventory();
            }
        }
    }
}
