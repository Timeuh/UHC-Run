package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.scenarios.Scenarios;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameListener implements Listener {

    private UHCRun uhcRun;
    private PlayerTeams teams;

    public GameListener(UHCRun UHCRun, PlayerTeams teams) {
        this.uhcRun = UHCRun;
        this.teams = teams;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        teams.joinScoreboard(player);
        if (!uhcRun.getPlayers().contains(player)) {
            uhcRun.getPlayers().add(player);
        }
        if (player.isOp()){
            player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
            ItemStack selectionScenarios = new ItemStack(Material.BOOK);
            ItemMeta selectionScenariosMeta = selectionScenarios.getItemMeta();
            selectionScenariosMeta.setDisplayName(ChatColor.GOLD + "Selection des scenarios");
            selectionScenarios.setItemMeta(selectionScenariosMeta);
            player.getInventory().setItem(0, selectionScenarios);
            player.updateInventory();
        } else {
            player.setPlayerListName(ChatColor.AQUA + "[Joueur] " + ChatColor.GOLD + player.getName());
        }
        if (uhcRun.isState(GameState.STARTING) || uhcRun.isState(GameState.PLAYING) || uhcRun.isState(GameState.FIGHTING)){
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le jeu est en cours");
            event.setJoinMessage(null);
            return;
        } else {
            player.setGameMode(GameMode.ADVENTURE);
            ItemStack selectionEquipe = new ItemStack(Material.BANNER);
            ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
            selectionEquipeMeta.setDisplayName(ChatColor.GOLD + "Selection de l'equipe");
            selectionEquipe.setItemMeta(selectionEquipeMeta);
            player.getInventory().setItem(4, selectionEquipe);
            player.updateInventory();
            event.setJoinMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " Rejoint les runners");
        }
        for (Player present : uhcRun.getPlayers()) {
            uhcRun.createLobbyBoard(teams);
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
        if (teams.hasTeam(player)){
            teams.leaveTeam(player);
        }
        event.setQuitMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " Quitte les runners");
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        ItemStack selectionEquipe = new ItemStack(Material.BANNER);
        ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
        selectionEquipeMeta.setDisplayName(ChatColor.GOLD + "Selection de l'equipe");
        selectionEquipe.setItemMeta(selectionEquipeMeta);

        if (item == null) return;

        if (item.getType().equals(Material.BANNER) && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Selection de l'equipe") && uhcRun.isScenarios(Scenarios.TEAMS)) {
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu Selection d'equipe");
                ItemStack redWool = teams.getTeamWool("RED");
                inv.setItem(4, redWool);
                player.openInventory(inv);
            }
        } else if (item.getType().equals(Material.BOOK) && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Selection des scenarios")) {
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu Selection des scenarios");
                ItemStack scenarTeam = new ItemStack(Material.BANNER);
                ItemMeta scenarTeamMeta = scenarTeam.getItemMeta();
                scenarTeamMeta.setDisplayName(ChatColor.GOLD + "Activer le scenario " + ChatColor.DARK_RED + "TEAMS");
                scenarTeam.setItemMeta(scenarTeamMeta);
                inv.setItem(0, scenarTeam);
                player.openInventory(inv);
            }
        } else if (item.isSimilar(selectionEquipe)){
            if (uhcRun.isScenarios(Scenarios.NOTEAMS)){
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "est desactive");
            }
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        ItemStack scenarTeam = new ItemStack(Material.BANNER);
        ItemMeta scenarTeamMeta = scenarTeam.getItemMeta();
        scenarTeamMeta.setDisplayName(ChatColor.GOLD + "Activer le scenario " + ChatColor.DARK_RED + "TEAMS");
        scenarTeam.setItemMeta(scenarTeamMeta);

        ItemStack selectionEquipe = new ItemStack(Material.BANNER);
        ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
        selectionEquipeMeta.setDisplayName(ChatColor.GOLD + "Selection de l'equipe");
        selectionEquipe.setItemMeta(selectionEquipeMeta);

        if (current == null) return;

        if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu Selection d'equipe")){
            event.setCancelled(true);
            if (current.isSimilar(teams.getTeamWool("RED"))){
                teams.joinTeam(player, "RED");
                player.closeInventory();
            }
        } else if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu Selection des scenarios")){
            event.setCancelled(true);
            if (current.isSimilar(scenarTeam)){
                uhcRun.setScenarios(Scenarios.TEAMS);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "active");
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

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();

        ItemStack selectionEquipe = new ItemStack(Material.BANNER);
        ItemMeta selectionEquipeMeta = selectionEquipe.getItemMeta();
        selectionEquipeMeta.setDisplayName(ChatColor.GOLD + "Selection de l'equipe");
        selectionEquipe.setItemMeta(selectionEquipeMeta);

        ItemStack selectionScenarios = new ItemStack(Material.BOOK);
        ItemMeta selectionScenariosMeta = selectionScenarios.getItemMeta();
        selectionScenariosMeta.setDisplayName(ChatColor.GOLD + "Selection des scenarios");
        selectionScenarios.setItemMeta(selectionScenariosMeta);

        if ((item.isSimilar(selectionEquipe) || item.isSimilar(selectionScenarios)) && uhcRun.isState(GameState.WAITING)){
            event.setCancelled(true);
        }
    }
}
