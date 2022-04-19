package fr.timeuh.inventories;

import fr.timeuh.UHCRun;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ScenarioSelect implements Listener {
    private UHCRun uhcRun;

    public ScenarioSelect(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item.isSimilar(getScenarioSelection()) && action == Action.RIGHT_CLICK_AIR){
            Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu sélection des scenarios");
            inv.setItem(2, getAllScenarioItems().get(0));
            inv.setItem(3, getAllScenarioItems().get(1));
            inv.setItem(4, getAllScenarioItems().get(2));
            inv.setItem(5, getAllScenarioItems().get(3));
            inv.setItem(6, getAllScenarioItems().get(4));
            inv.setItem(7, getAllScenarioItems().get(5));
            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();

        if (current != null && inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu sélection des scenarios")){
            event.setCancelled(true);
            if (current.isSimilar(getAllScenarioItems().get(0))){
                if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)){
                    uhcRun.getScenario().disableScenario(Scenario.TEAMS);
                    uhcRun.getScenario().enableScenario(Scenario.NOTEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Teams " + ChatColor.GOLD + "désactivé. Cela active le scenario" +
                            ChatColor.DARK_RED + " No Teams");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.TEAMS);
                    uhcRun.getScenario().disableScenario(Scenario.NOTEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Teams " + ChatColor.GOLD + "activé. Cela désactive le scenario" +
                            ChatColor.DARK_RED + " No Teams");
                }
            } else if (current.isSimilar(getAllScenarioItems().get(1))){
                if (uhcRun.getScenario().isEnabled(Scenario.NOTEAMS)){
                    uhcRun.getScenario().disableScenario(Scenario.NOTEAMS);
                    uhcRun.getScenario().enableScenario(Scenario.TEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "No Teams " + ChatColor.GOLD + "désactivé. Cela active le scenario" +
                            ChatColor.DARK_RED + " Teams");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.NOTEAMS);
                    uhcRun.getScenario().disableScenario(Scenario.TEAMS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "No Teams " + ChatColor.GOLD + "activé. Cela désactive le scenario" +
                            ChatColor.DARK_RED + " Teams");
                }
            } else if (current.isSimilar(getAllScenarioItems().get(2))){
                if (uhcRun.getScenario().isEnabled(Scenario.FRIENDLYFIRE)){
                    uhcRun.getScenario().disableScenario(Scenario.FRIENDLYFIRE);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Friendly Fire " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.FRIENDLYFIRE);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Friendly Fire " + ChatColor.GOLD + "activé");
                }
            } else if (current.isSimilar(getAllScenarioItems().get(3))){
                if (uhcRun.getScenario().isEnabled(Scenario.TIMBER)){
                    uhcRun.getScenario().disableScenario(Scenario.TIMBER);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Timber " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.TIMBER);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Timber " + ChatColor.GOLD + "activé");
                }
            } else if (current.isSimilar(getAllScenarioItems().get(4))){
                if (uhcRun.getScenario().isEnabled(Scenario.CUTCLEAN)){
                    uhcRun.getScenario().disableScenario(Scenario.CUTCLEAN);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Cutclean " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.CUTCLEAN);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Cutclean " + ChatColor.GOLD + "activé");
                }
            } else if (current.isSimilar(getAllScenarioItems().get(5))){
                if (uhcRun.getScenario().isEnabled(Scenario.HASTEYBOYS)){
                    uhcRun.getScenario().disableScenario(Scenario.HASTEYBOYS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Hastey Boys " + ChatColor.GOLD + "désactivé");
                } else {
                    uhcRun.getScenario().enableScenario(Scenario.HASTEYBOYS);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Scenario " + ChatColor.DARK_RED + "Hastey Boys " + ChatColor.GOLD + "activé");
                }
            }
        }
    }

    @EventHandler
    public void onConnection(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (player.isOp()) player.getInventory().setItem(0, getScenarioSelection());
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
        metaTeams.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver les teams");
        teams.setItemMeta(metaTeams);

        ItemStack noTeams = new ItemStack(Material.GOLD_SWORD);
        ItemMeta metaNoTeams = noTeams.getItemMeta();
        metaNoTeams.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver le FFA");
        noTeams.setItemMeta(metaNoTeams);

        ItemStack friendlyFire = new ItemStack(Material.YELLOW_FLOWER);
        ItemMeta metaFriendlyFire = friendlyFire.getItemMeta();
        metaFriendlyFire.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver le friendly fire (uniquement si les teams sont activées)");
        friendlyFire.setItemMeta(metaFriendlyFire);

        ItemStack timber = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta metaTimber = timber.getItemMeta();
        metaTimber.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver timber");
        timber.setItemMeta(metaTimber);

        ItemStack cutclean = new ItemStack(Material.FURNACE);
        ItemMeta metaCutclean = cutclean.getItemMeta();
        metaCutclean.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver le cutclean");
        cutclean.setItemMeta(metaCutclean);

        ItemStack hasteyBoys = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta metaHasteyBoys = hasteyBoys.getItemMeta();
        metaHasteyBoys.setDisplayName(ChatColor.DARK_GREEN + "Activer/désactiver hasteyBoys");
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
