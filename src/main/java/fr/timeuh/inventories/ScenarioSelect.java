package fr.timeuh.inventories;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import fr.timeuh.scenario.Scenario;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ScenarioSelect implements Listener {
    private UHCRun uhcRun;
    private List<ItemStack> scenarItems;

    public ScenarioSelect(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        scenarItems = getAllScenarioItems();
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;
        if (item.isSimilar(getScenarioSelection()) && action == Action.RIGHT_CLICK_AIR){
            Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu sélection des scenarios");
            inv.setItem(2, scenarItems.get(0));
            inv.setItem(3, scenarItems.get(1));
            inv.setItem(4, scenarItems.get(2));
            inv.setItem(5, scenarItems.get(3));
            inv.setItem(6, scenarItems.get(4));
            inv.setItem(7, scenarItems.get(5));
            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();

        if (current == null) return;
        if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu sélection des scenarios")){
            event.setCancelled(true);
            if (current.isSimilar(scenarItems.get(0))){
                if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)){
                    uhcRun.getScenario().disableScenario(Scenario.TEAMS);
                    replaceScenarioItem(inv, current, false);
                    if (uhcRun.getScenario().isEnabled(Scenario.FRIENDLYFIRE)){
                        uhcRun.getScenario().disableScenario(Scenario.FRIENDLYFIRE);
                        replaceScenarioItem(inv, scenarItems.get(2), false);
                    }
                    uhcRun.getScenario().enableScenario(Scenario.NOTEAMS);
                    replaceScenarioItem(inv, scenarItems.get(1), true);
                    for (Player sbPlayer : uhcRun.getPlayers().allCoPlayers()) uhcRun.getTeams().leaveTeam(sbPlayer);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Teams " + ChatColor.GOLD + "désactivé. Cela active le scenario" +
                            ChatColor.DARK_RED + " No Teams");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.TEAMS);
                    replaceScenarioItem(inv, current, true);
                    uhcRun.getScenario().disableScenario(Scenario.NOTEAMS);
                    replaceScenarioItem(inv, scenarItems.get(1), false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Teams " + ChatColor.GOLD + "activé. Cela désactive le scenario" +
                            ChatColor.DARK_RED + " No Teams");
                }
            } else if (current.isSimilar(scenarItems.get(1))){
                if (uhcRun.getScenario().isEnabled(Scenario.NOTEAMS)){
                    uhcRun.getScenario().disableScenario(Scenario.NOTEAMS);
                    replaceScenarioItem(inv, current, false);
                    uhcRun.getScenario().enableScenario(Scenario.TEAMS);
                    replaceScenarioItem(inv, inv.getItem(2), true);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "No Teams " + ChatColor.GOLD + "désactivé. Cela active le scenario" +
                            ChatColor.DARK_RED + " Teams");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.NOTEAMS);
                    replaceScenarioItem(inv, current, true);
                    uhcRun.getScenario().disableScenario(Scenario.TEAMS);
                    replaceScenarioItem(inv, inv.getItem(2), false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "No Teams " + ChatColor.GOLD + "activé. Cela désactive le scenario" +
                            ChatColor.DARK_RED + " Teams");
                }
            } else if (current.isSimilar(scenarItems.get(2))){
                if (uhcRun.getScenario().isEnabled(Scenario.FRIENDLYFIRE)){
                    uhcRun.getScenario().disableScenario(Scenario.FRIENDLYFIRE);
                    replaceScenarioItem(inv, current, false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Friendly Fire " + ChatColor.GOLD + "désactivé");
                } else {
                    if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)) {
                        uhcRun.getScenario().enableScenario(Scenario.FRIENDLYFIRE);
                        replaceScenarioItem(inv, current, true);
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Friendly Fire " + ChatColor.GOLD + "activé");
                    } else {
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Vous devez activer le scenario " + ChatColor.DARK_RED + "Teams " + ChatColor.GOLD + "pour" +
                                " activer le Friendly Fire");
                    }
                }
            } else if (current.isSimilar(scenarItems.get(3))){
                if (uhcRun.getScenario().isEnabled(Scenario.TIMBER)){
                    uhcRun.getScenario().disableScenario(Scenario.TIMBER);
                    replaceScenarioItem(inv, current, false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Timber " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.TIMBER);
                    replaceScenarioItem(inv, current, true);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Timber " + ChatColor.GOLD + "activé");
                }
            } else if (current.isSimilar(scenarItems.get(4))){
                if (uhcRun.getScenario().isEnabled(Scenario.CUTCLEAN)){
                    uhcRun.getScenario().disableScenario(Scenario.CUTCLEAN);
                    replaceScenarioItem(inv, current, false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Cutclean " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.CUTCLEAN);
                    replaceScenarioItem(inv, current, true);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Cutclean " + ChatColor.GOLD + "activé");
                }
            } else if (current.isSimilar(scenarItems.get(5))){
                if (uhcRun.getScenario().isEnabled(Scenario.HASTEYBOYS)){
                    uhcRun.getScenario().disableScenario(Scenario.HASTEYBOYS);
                    replaceScenarioItem(inv, current, false);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Hastey Boys " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.HASTEYBOYS);
                    replaceScenarioItem(inv, current, true);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Hastey Boys " + ChatColor.GOLD + "activé");
                }
            }
        }
    }

    @EventHandler
    public void onConnection(PlayerJoinEvent event){
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)) {
            Player player = event.getPlayer();
            if (player.isOp()) player.getInventory().setItem(0, getScenarioSelection());
        }
    }

    private void replaceScenarioItem(Inventory inventory, ItemStack item, boolean activation){
        String activationStatus;
        ChatColor color;
        if (activation){
            activationStatus = "activé";
            color = ChatColor.DARK_GREEN;
        } else {
            activationStatus = "désactivé";
            color = ChatColor.DARK_RED;
        }

        if (item.isSimilar(scenarItems.get(0))){
            ItemStack team = inventory.getItem(2);
            ItemMeta metaTeam = team.getItemMeta();
            metaTeam.setDisplayName(color + "Teams " + activationStatus);
            team.setItemMeta(metaTeam);
            inventory.setItem(2, team);
            scenarItems.set(0, team);
        } else if (item.isSimilar(scenarItems.get(1))){
            ItemStack ffa = inventory.getItem(3);
            ItemMeta metaFFA = ffa.getItemMeta();
            metaFFA.setDisplayName(color + "FFA " + activationStatus);
            ffa.setItemMeta(metaFFA);
            inventory.setItem(3, ffa);
            scenarItems.set(1, ffa);
        } else if (item.isSimilar(scenarItems.get(2))){
            ItemStack friendlyFire = inventory.getItem(4);
            ItemMeta metaFriendlyFire = friendlyFire.getItemMeta();
            metaFriendlyFire.setDisplayName(color + "Friendly fire " + activationStatus);
            friendlyFire.setItemMeta(metaFriendlyFire);
            inventory.setItem(4, friendlyFire);
            scenarItems.set(2, friendlyFire);
        } else if (item.isSimilar(scenarItems.get(3))){
            ItemStack timber = inventory.getItem(5);
            ItemMeta metaTimber = timber.getItemMeta();
            metaTimber.setDisplayName(color + "Timber " + activationStatus);
            timber.setItemMeta(metaTimber);
            inventory.setItem(5, timber);
            scenarItems.set(3, timber);
        } else if (item.isSimilar(scenarItems.get(4))){
            ItemStack cutclean = inventory.getItem(6);
            ItemMeta metaCutclean = cutclean.getItemMeta();
            metaCutclean.setDisplayName(color + "Cutclean " + activationStatus);
            cutclean.setItemMeta(metaCutclean);
            inventory.setItem(6, cutclean);
            scenarItems.set(4, cutclean);
        } else if (item.isSimilar(scenarItems.get(5))){
            ItemStack hasteyboys = inventory.getItem(7);
            ItemMeta metaHasteyboys = hasteyboys.getItemMeta();
            metaHasteyboys.setDisplayName(color + "HasteyBoys " + activationStatus);
            hasteyboys.setItemMeta(metaHasteyboys);
            inventory.setItem(7, hasteyboys);
            scenarItems.set(5, hasteyboys);
        }
    }

    private ItemStack getScenarioSelection(){
        ItemStack scenario = new ItemStack(Material.BOOK);
        ItemMeta metaScenario = scenario.getItemMeta();
        metaScenario.setDisplayName(ChatColor.GOLD + "Sélection des scenarios");
        scenario.setItemMeta(metaScenario);
        return scenario;
    }

    private List<ItemStack> getAllScenarioItems(){
        ItemStack teams = new ItemStack(Material.BANNER);
        ItemMeta metaTeams = teams.getItemMeta();
        metaTeams.setDisplayName(ChatColor.DARK_RED + "Teams désactivé");
        teams.setItemMeta(metaTeams);

        ItemStack noTeams = new ItemStack(Material.GOLD_SWORD);
        ItemMeta metaNoTeams = noTeams.getItemMeta();
        metaNoTeams.setDisplayName(ChatColor.DARK_GREEN + "FFA activé");
        metaNoTeams.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        noTeams.setItemMeta(metaNoTeams);

        ItemStack friendlyFire = new ItemStack(Material.YELLOW_FLOWER);
        ItemMeta metaFriendlyFire = friendlyFire.getItemMeta();
        metaFriendlyFire.setDisplayName(ChatColor.DARK_RED + "Friendly fire désactivé");
        friendlyFire.setItemMeta(metaFriendlyFire);

        ItemStack timber = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta metaTimber = timber.getItemMeta();
        metaTimber.setDisplayName(ChatColor.DARK_RED + "Timber désactivé");
        metaTimber.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        timber.setItemMeta(metaTimber);

        ItemStack cutclean = new ItemStack(Material.FURNACE);
        ItemMeta metaCutclean = cutclean.getItemMeta();
        metaCutclean.setDisplayName(ChatColor.DARK_RED + "Cutclean désactivé");
        cutclean.setItemMeta(metaCutclean);

        ItemStack hasteyBoys = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta metaHasteyBoys = hasteyBoys.getItemMeta();
        metaHasteyBoys.setDisplayName(ChatColor.DARK_RED + "HasteyBoys désactivé");
        metaHasteyBoys.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hasteyBoys.setItemMeta(metaHasteyBoys);

        List<ItemStack> scenarList = new ArrayList<>();
        scenarList.add(teams);
        scenarList.add(noTeams);
        scenarList.add(friendlyFire);
        scenarList.add(timber);
        scenarList.add(cutclean);
        scenarList.add(hasteyBoys);
        return scenarList;
    }
}
