package fr.timeuh.uhcrun.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;

public class PlayerTeams {
    public Scoreboard board;

    public PlayerTeams(){
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewObjective("UHCRunLobby","dummy");
        board.registerNewObjective("UHCRunPVP","dummy");
        board.registerNewObjective("UHCRun","dummy");

        Team redTeam = board.registerNewTeam("redTeam");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
    }

    public void joinTeam(Player player, String team){
        if (team.equals("RED")){
            Team join = board.getTeam("redTeam");
            join.addEntry(player.getName());
            player.setDisplayName(ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
            player.setPlayerListName(ChatColor.DARK_RED + player.getName());
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'equipe " + ChatColor.DARK_RED + "ROUGE");
        }
    }

    public ItemStack getTeamWool(String color){
        if (color.equals("RED")) {
            ItemStack redWool = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
            ItemMeta metaRedWool = redWool.getItemMeta();
            metaRedWool.setDisplayName(ChatColor.DARK_RED + "Equipe rouge");
            redWool.setItemMeta(metaRedWool);
            return redWool;
        } else {
            return null;
        }
    }

    public void joinScoreboard(Player player){
        player.setScoreboard(board);
    }
}
