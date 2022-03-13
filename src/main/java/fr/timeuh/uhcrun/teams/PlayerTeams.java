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

import java.util.ArrayList;
import java.util.List;

public class PlayerTeams {

    public static void joinScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewObjective("UHCRunLobby","dummy");
        board.registerNewObjective("UHCRunPVP","dummy");
        board.registerNewObjective("UHCRun","dummy");

        Team redTeam = board.registerNewTeam("Rouge");
        redTeam.setPrefix(ChatColor.DARK_RED + "");

        Team blueTeam = board.registerNewTeam("Bleue");
        blueTeam.setPrefix(ChatColor.BLUE + "");

        player.setScoreboard(board);
    }

    public static void joinTeam(Player player, String team, UHCRun uhcRun){
        for (Player sbPlayer : uhcRun.getPlayers()) {
            Scoreboard board = sbPlayer.getScoreboard();
            switch (team) {
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

                default:
                    break;
            }
        }
        switch (team) {
            case "RED":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.DARK_RED + "ROUGE");
                break;

            case "BLUE":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.BLUE + "BLEUE");
                break;

            default:
                break;
        }
    }

    public static void leaveTeam(Player player, UHCRun uhcRun){
        for (Player sbPlayer : uhcRun.getPlayers()) {
            for (Team team : getPlayerTeamList(sbPlayer)) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                    player.setDisplayName(ChatColor.WHITE + player.getName());
                    if (player.isOp()) {
                        player.setPlayerListName(ChatColor.DARK_RED + "[OP] " + ChatColor.GOLD + player.getName());
                    } else {
                        player.setPlayerListName(ChatColor.AQUA + "[Joueur] " + ChatColor.GOLD + player.getName());
                    }
                }
            }
        }
    }

    public static void emptyTeams(UHCRun uhcRun){
        for (Player player : uhcRun.getPlayers()){
            leaveTeam(player, uhcRun);
        }
    }

    public static boolean hasTeam(Player player){
        for (Team team : getPlayerTeamList(player)){
            if (team.hasEntry(player.getName())){
                return true;
            }
        }
        return false;
    }

    public static boolean isTeamEliminated(Player player,Team toCheck){
        for (Team team : getPlayerTeamList(player)){
            if (team.equals(toCheck)){
                return false;
            }
        }
        return true;
    }

    public static void updateTeams(UHCRun uhcRun){
        for (Player player : uhcRun.getPlayers()) {
            List<Team> teamList = getPlayerTeamList(player);
            teamList.removeIf(team -> team.getEntries().size() == 0);
        }
    }

    public static boolean oneTeamRemaining(Player player){
        return getPlayerTeamList(player).size() == 1;
    }

    public static Team getLastTeam(Player player){
        if (oneTeamRemaining(player)){
            return getPlayerTeamList(player).get(0);
        } else {
            return null;
        }
    }

    public static List<Team> getPlayerTeamList(Player player){
        return new ArrayList<>(player.getScoreboard().getTeams());
    }

    public static ChatColor getTeamColor(Team toCheck){
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

    public static ItemStack getTeamWool(String color){
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
}
