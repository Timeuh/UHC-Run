package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.tasks.GameState;
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
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameListener implements Listener {

    private UHCRun uhcRun;
    private static List<ItemStack> usefulItems = new ArrayList<>();
    private HashMap<UUID, Team> decoTeams;

    public GameListener(UHCRun UHCRun) {
        this.uhcRun = UHCRun;
        this.decoTeams = new HashMap<>();
        createUsefulItems();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (uhcRun.isState(GameState.WAITING) || uhcRun.isState(GameState.FINISH)) {
            ItemStack scenarioSelection = findItem(ChatColor.GOLD + "Sélection des scenarios");
            ItemStack teamSelection = findItem(ChatColor.GOLD + "Sélection de l'équipe");
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().setItem(4, teamSelection);
            player.updateInventory();
            for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 99999, 1, true, false));
            event.setJoinMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " Rejoint les runners");
            player.setStatistic(Statistic.PLAYER_KILLS, 0);
            PlayerTeams.joinScoreboard(player);
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Salut ! si tu as besoin d'informations pense au" + ChatColor.DARK_RED + " /help");
            uhcRun.getPlayers().add(player.getUniqueId());
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
            player.setLevel(0);
            player.setExp(0f);
            player.setHealth(20);
            if (player.isOp()) {
                player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
                player.getInventory().setItem(0, scenarioSelection);
                player.updateInventory();
            } else {
                player.setPlayerListName(ChatColor.AQUA + "[Joueur] " + ChatColor.GOLD + player.getName());
            }
            PlayerTeams.updateScoreboard(uhcRun);
        } else {
            if (uhcRun.getAlivePlayers().contains(player.getUniqueId())) {
                PlayerTeams.rejoinScoreboard(player);
                if (!uhcRun.checkEnabledScenario(Scenarios.TEAMS)) {
                    if (player.isOp()) {
                        player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
                    } else {
                        player.setPlayerListName(ChatColor.AQUA + "[Joueur] " + ChatColor.GOLD + player.getName());
                    }
                } else {
                    Team playerTeam = decoTeams.get(player.getUniqueId());
                    PlayerTeams.joinTeam(player, playerTeam.getName(), uhcRun);
                    PlayerTeams.updateScoreboard(uhcRun);
                }
                event.setJoinMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " s'est reconnecté");
            } else {
                event.setJoinMessage(null);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le jeu est en cours");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (uhcRun.isState(GameState.WAITING) || uhcRun.isState(GameState.FINISH)) {
            uhcRun.getPlayers().remove(player.getUniqueId());
            if (uhcRun.getAlivePlayers().contains(player.getUniqueId())) uhcRun.getAlivePlayers().remove(player.getUniqueId());
            for (Player sbPlayer : uhcRun.getActualPlayers()) uhcRun.createLobbyBoard(sbPlayer);
        } else {
            if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)){
                Team playerTeam = PlayerTeams.getPlayerTeam(player);
                decoTeams.put(player.getUniqueId(), playerTeam);
                PlayerTeams.leaveTeamIngame(player, uhcRun);
            }
        }
        event.setQuitMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " Quitte les runners");
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        ItemStack teamSelection = findItem(ChatColor.GOLD + "Sélection de l'équipe");
        ItemStack teamScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TEAMS");
        ItemStack scenarioSelection = findItem(ChatColor.GOLD + "Sélection des scenarios");
        ItemStack friendlyFireScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "FRIENDLYFIRE");
        ItemStack cutcleanScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "CUTCLEAN");
        ItemStack hasteyBoysScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "HASTEYBOYS");
        ItemStack timberScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TIMBER");

        if (item == null) return;

        if (item.isSimilar(teamSelection) && uhcRun.checkEnabledScenario(Scenarios.TEAMS)){
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu sélection d'équipe");
                inv.setItem(0, PlayerTeams.getTeamWool("RED"));
                inv.setItem(1, PlayerTeams.getTeamWool("ORANGE"));
                inv.setItem(2, PlayerTeams.getTeamWool("YELLOW"));
                inv.setItem(3, PlayerTeams.getTeamWool("GREEN"));
                inv.setItem(4, PlayerTeams.getTeamWool("BLUE"));
                inv.setItem(5, PlayerTeams.getTeamWool("PINK"));
                inv.setItem(6, PlayerTeams.getTeamWool("PURPLE"));
                inv.setItem(7, PlayerTeams.getTeamWool("GRAY"));
                inv.setItem(8, PlayerTeams.getTeamWool("BLACK"));
                player.openInventory(inv);
            }
        } else if (item.isSimilar(scenarioSelection)){
            if (action == Action.RIGHT_CLICK_AIR) {
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu sélection des scenarios");
                inv.setItem(2, teamScenario);
                inv.setItem(3, friendlyFireScenario);
                inv.setItem(4,cutcleanScenario);
                inv.setItem(5, hasteyBoysScenario);
                inv.setItem(6, timberScenario);
                player.openInventory(inv);
            }
        } else if (item.isSimilar(teamSelection) && uhcRun.checkEnabledScenario(Scenarios.NOTEAMS)){
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "est désactivé");
        }
    }

    @EventHandler
    public void onClick (InventoryClickEvent event){
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();
        ItemStack teamScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TEAMS");
        ItemStack friendlyFireScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "FRIENDLYFIRE");
        ItemStack cutcleanScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "CUTCLEAN");
        ItemStack hasteyBoysScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "HASTEYBOYS");
        ItemStack timberScenario = findItem(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TIMBER");

        if (current == null) return;

        if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu sélection d'équipe")){
            event.setCancelled(true);
            if (current.isSimilar(PlayerTeams.getTeamWool("RED"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "RED", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("BLUE"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "BLUE", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("ORANGE"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "ORANGE", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("GREEN"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "GREEN", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("PINK"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "PINK", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("PURPLE"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "PURPLE", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("YELLOW"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "YELLOW", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("GRAY"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "GRAY", uhcRun);
                player.closeInventory();
            } else if (current.isSimilar(PlayerTeams.getTeamWool("BLACK"))){
                PlayerTeams.leaveTeam(player, uhcRun);
                PlayerTeams.joinTeam(player, "BLACK", uhcRun);
                player.closeInventory();
            }
        } else if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu sélection des scenarios")){
            event.setCancelled(true);
            if (current.isSimilar(teamScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)){
                    if (uhcRun.checkEnabledScenario(Scenarios.FRIENDLYFIRE)){
                        uhcRun.disableScenario(Scenarios.FRIENDLYFIRE);
                    }
                    uhcRun.disableScenario(Scenarios.TEAMS);
                    uhcRun.enableScenarios(Scenarios.NOTEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "désactivé");
                    PlayerTeams.emptyTeams(uhcRun);
                    player.closeInventory();
                } else {
                    uhcRun.enableScenarios(Scenarios.TEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TEAMS " + ChatColor.GOLD + "activé");
                    uhcRun.disableScenario(Scenarios.NOTEAMS);
                    player.closeInventory();
                }

            } else if (current.isSimilar(friendlyFireScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.TEAMS) && !uhcRun.checkEnabledScenario(Scenarios.FRIENDLYFIRE)){
                    uhcRun.enableScenarios(Scenarios.FRIENDLYFIRE);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "FRIENDLYFIRE " + ChatColor.GOLD + "activé");
                    player.closeInventory();
                } else if (uhcRun.checkEnabledScenario(Scenarios.NOTEAMS)){
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le scenario " + ChatColor.DARK_RED + "NOTEAMS " + ChatColor.GOLD + " est activé, veuillez activer" +
                            " les teams avant d'activer le scénario " + ChatColor.DARK_RED + "FRIENDLYFIRE");
                    player.closeInventory();
                } else if (uhcRun.checkEnabledScenario(Scenarios.FRIENDLYFIRE)){
                    uhcRun.disableScenario(Scenarios.FRIENDLYFIRE);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "FRIENDLYFIRE " + ChatColor.GOLD + "désactivé");
                    player.closeInventory();
                }

            } else if (current.isSimilar(cutcleanScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.CUTCLEAN)){
                    uhcRun.disableScenario(Scenarios.CUTCLEAN);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "CUTCLEAN " + ChatColor.GOLD + "désactivé");
                    player.closeInventory();
                } else {
                    uhcRun.enableScenarios(Scenarios.CUTCLEAN);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "CUTCLEAN " + ChatColor.GOLD + "activé");
                    player.closeInventory();
                }
            } else if (current.isSimilar(hasteyBoysScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.HASTEYBOYS)){
                    uhcRun.disableScenario(Scenarios.HASTEYBOYS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "HASTEYBOYS " + ChatColor.GOLD + "désactivé");
                    player.closeInventory();
                } else {
                    uhcRun.enableScenarios(Scenarios.HASTEYBOYS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "HASTEYBOYS " + ChatColor.GOLD + "activé");
                    player.closeInventory();
                }
            } else if (current.isSimilar(timberScenario)){
                if (uhcRun.checkEnabledScenario(Scenarios.TIMBER)){
                    uhcRun.disableScenario(Scenarios.TIMBER);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TIMBER " + ChatColor.GOLD + "désactivé");
                    player.closeInventory();
                } else {
                    uhcRun.enableScenarios(Scenarios.TIMBER);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "TIMBER " + ChatColor.GOLD + "activé");
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
        ItemStack teamSelection = findItem(ChatColor.GOLD + "Sélection de l'équipe");
        ItemStack scenarioSelection = findItem(ChatColor.GOLD + "Sélection des scenarios");

        if ((item.isSimilar(teamSelection) || item.isSimilar(scenarioSelection)) && uhcRun.isState(GameState.WAITING)){
            event.setCancelled(true);
        }
    }

    public void createUsefulItems(){
        ItemStack teamSelection = new ItemStack(Material.BANNER);
        ItemMeta teamSelectionMeta = teamSelection.getItemMeta();
        teamSelectionMeta.setDisplayName(ChatColor.GOLD + "Sélection de l'équipe");
        teamSelection.setItemMeta(teamSelectionMeta);
        usefulItems.add(teamSelection);

        ItemStack scenarioSelection = new ItemStack(Material.BOOK);
        ItemMeta scenarioSelectionMeta = scenarioSelection.getItemMeta();
        scenarioSelectionMeta.setDisplayName(ChatColor.GOLD + "Sélection des scenarios");
        scenarioSelection.setItemMeta(scenarioSelectionMeta);
        usefulItems.add(scenarioSelection);

        ItemStack scenarTeam = new ItemStack(Material.BANNER);
        ItemMeta scenarTeamMeta = scenarTeam.getItemMeta();
        scenarTeamMeta.setDisplayName(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TEAMS");
        scenarTeam.setItemMeta(scenarTeamMeta);
        usefulItems.add(scenarTeam);

        ItemStack scenarFirendlyFire = new ItemStack(Material.FIREBALL);
        ItemMeta scenarFirendlyFireMeta = scenarFirendlyFire.getItemMeta();
        scenarFirendlyFireMeta.setDisplayName(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "FRIENDLYFIRE");
        scenarFirendlyFire.setItemMeta(scenarFirendlyFireMeta);
        usefulItems.add(scenarFirendlyFire);

        ItemStack scenarCutClean = new ItemStack(Material.FURNACE);
        ItemMeta scenarCutCleanMeta = scenarCutClean.getItemMeta();
        scenarCutCleanMeta.setDisplayName(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "CUTCLEAN");
        scenarCutClean.setItemMeta(scenarCutCleanMeta);
        usefulItems.add(scenarCutClean);

        ItemStack scenarHasteyBoys = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta scenarHasteyBoysMeta = scenarHasteyBoys.getItemMeta();
        scenarHasteyBoysMeta.setDisplayName(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "HASTEYBOYS");
        scenarHasteyBoysMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
        scenarHasteyBoys.setItemMeta(scenarHasteyBoysMeta);
        usefulItems.add(scenarHasteyBoys);

        ItemStack scenarTimber = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta scenarTimberMeta = scenarTimber.getItemMeta();
        scenarTimberMeta.setDisplayName(ChatColor.GOLD + "Activer/désactiver le scenario " + ChatColor.DARK_RED + "TIMBER");
        scenarTimberMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
        scenarTimber.setItemMeta(scenarTimberMeta);
        usefulItems.add(scenarTimber);
    }

    public static ItemStack findItem(String name){
        for (ItemStack item : usefulItems){
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }
}
