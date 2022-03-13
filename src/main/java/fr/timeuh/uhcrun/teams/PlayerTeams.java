package fr.timeuh.uhcrun.teams;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;

import java.util.*;

public class PlayerTeams {
    public Scoreboard board;
    public List<Team> teamList =  new ArrayList<>();

    public PlayerTeams(){
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewObjective("UHCRunLobby","dummy");
        board.registerNewObjective("UHCRunPVP","dummy");
        board.registerNewObjective("UHCRun","dummy");

        Team redTeam = board.registerNewTeam("Rouge");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
        teamList.add(redTeam);

        Team blueTeam = board.registerNewTeam("Bleue");
        blueTeam.setPrefix(ChatColor.BLUE + "");
        teamList.add(blueTeam);
    }

    public void joinTeam(Player player, String team){
        switch (team){
            case "RED":
                Team redTeam = board.getTeam("Rouge");
                redTeam.addEntry(player.getName());
                player.setDisplayName(ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.DARK_RED + player.getName());
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.DARK_RED + "ROUGE");
                break;

            case "BLUE":
                Team blueTeam = board.getTeam("Bleue");
                blueTeam.addEntry(player.getName());
                player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.BLUE + player.getName());
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.BLUE + "BLEUE");
                break;
        }
    }

    public void leaveTeam(Player player){
        for (Team team : teamList) {
            if (team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
                player.setDisplayName(ChatColor.WHITE + player.getName());
                if (player.isOp()){
                    player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
                } else {
                    player.setPlayerListName(ChatColor.AQUA + "[Joueur] " + ChatColor.GOLD + player.getName());
                }
            }
        }
    }

    public boolean hasTeam(Player player){
        for (Team team : teamList){
            if (team.hasEntry(player.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean isTeamEliminated(Team toCheck){
        for (Team team : teamList){
            if (team.equals(toCheck)){
                return false;
            }
        }
        return true;
    }

    public Team getPlayerTeam(Player player){
        for (Team team : teamList){
            if (team.getEntries().contains(player.getName())){
                return team;
            }
        }
        return null;
    }

    public void updateTeams(){
        teamList.removeIf(existingTeam -> existingTeam.getEntries().size() == 0);
    }

    public void emptyTeams(UHCRun uhcRun){
        for (Player player : uhcRun.getPlayers()){
            leaveTeam(player);
        }
    }

    public boolean oneTeamRemaining(){
        return teamList.size() == 1;
    }

    public Team getLastTeam(){
        if (oneTeamRemaining()){
            return teamList.get(0);
        } else {
            return null;
        }
    }

    public ChatColor getTeamColor(Team toCheck){
        String name = toCheck.getName();
        switch (name){
            case "Rouge":
                return ChatColor.DARK_RED;
            case "Bleue":
                return ChatColor.BLUE;
            default:
                break;
        }
        return ChatColor.WHITE;
    }

    public ItemStack getTeamWool(String color){
        switch (color){
            case "RED":
                ItemStack redWool = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
                ItemMeta metaRedWool = redWool.getItemMeta();
                metaRedWool.setDisplayName(ChatColor.DARK_RED + "Equipe rouge");
                redWool.setItemMeta(metaRedWool);
                return redWool;

            case "BLUE":
                ItemStack blueWool = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
                ItemMeta metaBlueWool = blueWool.getItemMeta();
                metaBlueWool.setDisplayName(ChatColor.BLUE + "Equipe bleue");
                blueWool.setItemMeta(metaBlueWool);
                return blueWool;

            default:
                return null;
        }
    }

    public void joinScoreboard(Player player){
        player.setScoreboard(board);
    }
}
