package fr.timeuh.inventories;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import fr.timeuh.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
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

public class TeamSelect implements Listener {

    private UHCRun uhcRun;

    public TeamSelect(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onInterraction(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (item == null) return;
        if (item.isSimilar(getTeamSelection()) && action == Action.RIGHT_CLICK_AIR){
            if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)){
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Menu sélection de l'équipe");
                inv.setItem(0, getTeamSelectionItems().get(0));
                inv.setItem(1, getTeamSelectionItems().get(1));
                inv.setItem(2, getTeamSelectionItems().get(2));
                inv.setItem(3, getTeamSelectionItems().get(3));
                inv.setItem(4, getTeamSelectionItems().get(4));
                inv.setItem(5, getTeamSelectionItems().get(5));
                inv.setItem(6, getTeamSelectionItems().get(6));
                inv.setItem(7, getTeamSelectionItems().get(7));
                inv.setItem(8, getTeamSelectionItems().get(8));
                player.openInventory(inv);
            } else {
                player.sendMessage(ChatColor.GOLD + "Le scénario Teams est désactivé, vous ne pouvez pas choisir de team");
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();

        if (current == null) return;
        if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Menu sélection de l'équipe")){
            event.setCancelled(true);
            if (current.isSimilar(getTeamSelectionItems().get(0))){
                //join red team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(1))){
                //join blue team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(2))){
                //join orange team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(3))){
                //join green team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(4))){
                //join pink team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(5))){
                //join purple team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(6))){
                //join yellow team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(7))){
                //join gray team
                player.closeInventory();
            } else if (current.isSimilar(getTeamSelectionItems().get(8))){
                //join black team
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)) {
            Player player = event.getPlayer();
            player.getInventory().setItem(4, getTeamSelection());
        }
    }

    private ItemStack getTeamSelection(){
        ItemStack teamSelect = new ItemStack(Material.BANNER);
        ItemMeta metaTeamSelect = teamSelect.getItemMeta();
        metaTeamSelect.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        metaTeamSelect.setDisplayName(ChatColor.GOLD + "Sélection de l'équipe");
        teamSelect.setItemMeta(metaTeamSelect);
        return teamSelect;
    }

    private List<ItemStack> getTeamSelectionItems(){
        ItemStack redTeam = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
        ItemMeta metaRedTeam = redTeam.getItemMeta();
        metaRedTeam.setDisplayName(ChatColor.DARK_RED + "Équipe rouge");
        redTeam.setItemMeta(metaRedTeam);

        ItemStack blueTeam = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
        ItemMeta metaBlueTeam = blueTeam.getItemMeta();
        metaBlueTeam.setDisplayName(ChatColor.DARK_BLUE + "Équipe blueue");
        blueTeam.setItemMeta(metaBlueTeam);

        ItemStack orangeTeam = new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getData());
        ItemMeta metaOrangeTeam = orangeTeam.getItemMeta();
        metaOrangeTeam.setDisplayName(ChatColor.GOLD + "Équipe orange");
        orangeTeam.setItemMeta(metaOrangeTeam);

        ItemStack greenTeam = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
        ItemMeta metaGreenTeam = greenTeam.getItemMeta();
        metaGreenTeam.setDisplayName(ChatColor.DARK_GREEN + "Équipe verte");
        greenTeam.setItemMeta(metaGreenTeam);

        ItemStack pinkTeam = new ItemStack(Material.WOOL, 1, DyeColor.PINK.getData());
        ItemMeta metaPinkTeam = pinkTeam.getItemMeta();
        metaPinkTeam.setDisplayName(ChatColor.LIGHT_PURPLE + "Équipe rose");
        pinkTeam.setItemMeta(metaPinkTeam);

        ItemStack purpleTeam = new ItemStack(Material.WOOL, 1, DyeColor.PURPLE.getData());
        ItemMeta metaPurpleTeam = purpleTeam.getItemMeta();
        metaPurpleTeam.setDisplayName(ChatColor.DARK_PURPLE + "Équipe violette");
        purpleTeam.setItemMeta(metaPurpleTeam);

        ItemStack yellowTeam = new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData());
        ItemMeta metaYellowTeam = yellowTeam.getItemMeta();
        metaYellowTeam.setDisplayName(ChatColor.YELLOW + "Équipe jaune");
        yellowTeam.setItemMeta(metaYellowTeam);

        ItemStack grayTeam = new ItemStack(Material.WOOL, 1, DyeColor.GRAY.getData());
        ItemMeta metaGrayTeam = grayTeam.getItemMeta();
        metaGrayTeam.setDisplayName(ChatColor.GRAY + "Équipe grise");
        grayTeam.setItemMeta(metaGrayTeam);

        ItemStack blackTeam = new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getData());
        ItemMeta metaBlackTeam = blackTeam.getItemMeta();
        metaBlackTeam.setDisplayName(ChatColor.BLACK + "Équipe noire");
        blackTeam.setItemMeta(metaBlackTeam);

        List<ItemStack> teamItems = new ArrayList<>();
        teamItems.add(redTeam);
        teamItems.add(blueTeam);
        teamItems.add(orangeTeam);
        teamItems.add(greenTeam);
        teamItems.add(pinkTeam);
        teamItems.add(purpleTeam);
        teamItems.add(yellowTeam);
        teamItems.add(grayTeam);
        teamItems.add(blackTeam);
        return teamItems;
    }
}


