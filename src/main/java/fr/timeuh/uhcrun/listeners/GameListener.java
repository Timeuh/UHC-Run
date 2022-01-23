package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GameListener implements Listener {

    private UHCRun uhcRun;

    public GameListener(UHCRun UHCRun) {
        this.uhcRun = UHCRun;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();

        if (!uhcRun.isState(GameState.WAITING)){
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("Le jeu est en cours");
            event.setJoinMessage(null);
            return;
        }

        if (!uhcRun.getPlayers().contains(player) && !uhcRun.getAlivePlayers().contains(player)){
            uhcRun.getPlayers().add(player);
            uhcRun.getAlivePlayers().add(player);
            event.setJoinMessage("§5[UHCRun] §c" + player.getName() + " Rejoint les runners");
        }

        player.setGameMode(GameMode.ADVENTURE);
        ItemStack selectionEquipe = new ItemStack(Material.STICK);
        ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
        selectionEquipeMeta.setDisplayName("§6Sélection de l'équipe");
        selectionEquipe.setItemMeta(selectionEquipeMeta);
        player.getInventory().setItem(4, selectionEquipe);
        player.updateInventory();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (uhcRun.getPlayers().contains(player) && uhcRun.getAlivePlayers().contains(player)){
            uhcRun.getPlayers().remove(player);
            uhcRun.getAlivePlayers().remove(player);
        }

        event.setQuitMessage("§5[UHCRun] §c" +player.getName() + " Quitte les runners");
        uhcRun.checkWin(uhcRun);
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
                && item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Sélection de l'équipe")) {
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
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (!uhcRun.isState(GameState.PLAYING)){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (!uhcRun.isState(GameState.PLAYING)){
            event.setCancelled(true);
            return;
        }
    }
}
