package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.teamlists.PlayerTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
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
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        if (!uhcRun.getPlayers().contains(player)) {
            uhcRun.getPlayers().add(player);
        }
        if (player.isOp()){
            player.setPlayerListName("§4[OP] §6" + player.getName());
        } else {
            player.setPlayerListName("§3[Joueur] §6" + player.getName());
        }
        if (uhcRun.isState(GameState.STARTING) || uhcRun.isState(GameState.PLAYING) || uhcRun.isState(GameState.FIGHTING)){
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("§5[UHCRun] §6Le jeu est en cours");
            event.setJoinMessage(null);
            return;
        } else {
            player.setGameMode(GameMode.ADVENTURE);
            ItemStack selectionEquipe = new ItemStack(Material.STICK);
            ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
            selectionEquipeMeta.setDisplayName("§6Sélection de l'équipe");
            selectionEquipe.setItemMeta(selectionEquipeMeta);
            player.getInventory().setItem(4, selectionEquipe);
            player.updateInventory();
            event.setJoinMessage("§5[UHCRun] §4" + player.getName() + " §6Rejoint les runners");
        }
        for (Player present : uhcRun.getPlayers()) {
            uhcRun.createLobbyBoard(present);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (uhcRun.getPlayers().contains(player) && uhcRun.getAlivePlayers().contains(player)){
            uhcRun.getPlayers().remove(player);
            uhcRun.getAlivePlayers().remove(player);
        } else if (uhcRun.getPlayers().contains(player)){
            uhcRun.getPlayers().remove(player);
        }
        event.setQuitMessage("§5[UHCRun] §4" +player.getName() + " §6Quitte les runners");
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Sélection de l'équipe")) {
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
                PlayerTeam.setPlayerTeam(player, "RED");
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
            if (uhcRun.isState(GameState.WAITING) || uhcRun.isState(GameState.STARTING) || uhcRun.isState(GameState.FINISH)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (uhcRun.isState(GameState.WAITING) || uhcRun.isState(GameState.STARTING) || uhcRun.isState(GameState.FINISH)){
            event.setCancelled(true);
        }
    }
}
