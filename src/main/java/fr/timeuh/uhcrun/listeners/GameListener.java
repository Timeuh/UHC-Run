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

import java.util.ArrayList;
import java.util.List;

public class GameListener implements Listener {

    private final UHCRun uhcRun;
    private final PlayerTeams teams;
    private final List<ItemStack> usefulItems;

    public GameListener(UHCRun UHCRun, PlayerTeams teams) {
        this.uhcRun = UHCRun;
        this.teams = teams;
        this.usefulItems = createUsefulItems();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        teams.joinScoreboard(player);
        ItemStack scenarioSelection = findItem(ChatColor.GOLD + "Selection des scenarios");
        ItemStack teamSelection = findItem(ChatColor.GOLD + "Selection de l'equipe");
        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Salut ! si tu as besoin d'informations pense au" + ChatColor.DARK_RED + " /help");

        if (!uhcRun.getPlayers().contains(player)) {
            uhcRun.getPlayers().add(player);
        }
        if (player.isOp()){
            player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
            player.getInventory().setItem(0, scenarioSelection);
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
            player.getInventory().setItem(4, teamSelection);
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
        if (uhcRun.getPlayers().contains(player)) {
            uhcRun.getPlayers().remove(player);
            if (uhcRun.getAlivePlayers().contains(player)) {
                uhcRun.getAlivePlayers().remove(player);
            }
        }
        if (teams.hasTeam(player) && (uhcRun.isState(GameState.WAITING) || uhcRun.isState(GameState.FINISH))){
            teams.leaveTeam(player);
        }
        event.setQuitMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " Quitte les runners");
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        ItemStack teamSelection = findItem(ChatColor.GOLD + "Selection de l'equipe");
        ItemStack teamScenario = findItem(ChatColor.GOLD + "Activer/desactiver le scenario " + ChatColor.DARK_RED + "TEAMS");

        if (item == null) return;

        if (item.isSimilar(teamSelection) && uhcRun.checkEnabledScenario(Scenarios.TEAMS)) {
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu Selection d'equipe");
                ItemStack redWool = teams.getTeamWool("RED");
                inv.setItem(4, redWool);
                player.openInventory(inv);
            }
        } else if (item.getType().equals(Material.BOOK) && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Selection des scenarios")) {
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu Selection des scenarios");
                inv.setItem(0, teamScenario);
                player.openInventory(inv);
            }
        } else if (item.isSimilar(teamSelection) && uhcRun.checkEnabledScenario(Scenarios.NOTEAMS)){
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "est desactive");
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        ItemStack teamScenario = findItem(ChatColor.GOLD + "Activer le scenario " + ChatColor.DARK_RED + "TEAMS");

        if (current == null) return;

        if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu Selection d'equipe")){
            event.setCancelled(true);
            if (current.isSimilar(teams.getTeamWool("RED"))){
                teams.leaveTeam(player);
                teams.joinTeam(player, "RED");
                player.closeInventory();
            }
        } else if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu Selection des scenarios")){
            event.setCancelled(true);
            if (current.isSimilar(teamScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)){
                    uhcRun.disableScenario(Scenarios.TEAMS);
                    uhcRun.enableScenarios(Scenarios.NOTEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "desactive");
                    teams.emptyTeams(uhcRun);
                    player.closeInventory();
                } else {
                    uhcRun.enableScenarios(Scenarios.TEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "active");
                    uhcRun.disableScenario(Scenarios.NOTEAMS);
                    player.closeInventory();
                }
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
        ItemStack teamSelection = findItem(ChatColor.GOLD + "Selection de l'equipe");
        ItemStack scenarioSelection = findItem(ChatColor.GOLD + "Selection des scenarios");

        if ((item.isSimilar(teamSelection) || item.isSimilar(scenarioSelection)) && uhcRun.isState(GameState.WAITING)){
            event.setCancelled(true);
        }
    }

    public List<ItemStack> createUsefulItems(){
        List<ItemStack> itemList = new ArrayList<>();

        ItemStack teamSelection = new ItemStack(Material.BANNER);
        ItemMeta teamSelectionMeta = teamSelection.getItemMeta();
        teamSelectionMeta.setDisplayName(ChatColor.GOLD + "Selection de l'equipe");
        teamSelection.setItemMeta(teamSelectionMeta);
        itemList.add(teamSelection);

        ItemStack scenarioSelection = new ItemStack(Material.BOOK);
        ItemMeta scenarioSelectionMeta = scenarioSelection.getItemMeta();
        scenarioSelectionMeta.setDisplayName(ChatColor.GOLD + "Selection des scenarios");
        scenarioSelection.setItemMeta(scenarioSelectionMeta);
        itemList.add(scenarioSelection);

        ItemStack scenarTeam = new ItemStack(Material.BANNER);
        ItemMeta scenarTeamMeta = scenarTeam.getItemMeta();
        scenarTeamMeta.setDisplayName(ChatColor.GOLD + "Activer/desactiver le scenario " + ChatColor.DARK_RED + "TEAMS");
        scenarTeam.setItemMeta(scenarTeamMeta);
        itemList.add(scenarTeam);

        return itemList;
    }

    public ItemStack findItem(String name){
        for (ItemStack item : usefulItems){
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }
}
