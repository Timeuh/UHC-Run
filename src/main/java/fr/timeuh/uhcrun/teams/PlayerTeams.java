package fr.timeuh.uhcrun.teams;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.scenarios.Scenarios;
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

    public static Map<UUID, List<Team>> playerTeamsMap = new HashMap<>();

    public static void joinScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        List<Team> playerTeamList = new ArrayList<>();
        board.registerNewObjective("UHCRunLobby","dummy");
        board.registerNewObjective("UHCRunPVP","dummy");
        board.registerNewObjective("UHCRun","dummy");

        Team redTeam = board.registerNewTeam("Rouge");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
        playerTeamList.add(redTeam);

        Team blueTeam = board.registerNewTeam("Bleue");
        blueTeam.setPrefix(ChatColor.BLUE + "");
        playerTeamList.add(blueTeam);

        player.setScoreboard(board);
        playerTeamsMap.put(player.getUniqueId(), playerTeamList);
    }

    public static void updateScoreboard(UHCRun uhcRun){
        for (Player player : uhcRun.getPlayers()) {
            if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)) {
                if (hasTeam(player)) {
                    String playerTeamName = getPlayerTeam(player).getName();
                    switch (playerTeamName) {
                        case "Rouge":
                            joinTeam(player, "RED", uhcRun);
                            break;

                        case "Bleue":
                            joinTeam(player, "BLUE", uhcRun);
                            break;

                        default:
                            break;
                    }
                }
            }
            uhcRun.createLobbyBoard(player);
        }
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
                    break;

                case "BLUE":
                    Team blueTeam = board.getTeam("Bleue");
                    blueTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
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
            List<Team> teamList = playerTeamsMap.get(sbPlayer.getUniqueId());
            for (Team team : teamList ) {
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
        for (Team team : player.getScoreboard().getTeams()){
            if (team.hasEntry(player.getName())){
                return true;
            }
        }
        return false;
    }

    public static Team getPlayerTeam(Player player){
        for (Team team : player.getScoreboard().getTeams()){
            if (team.hasEntry(player.getName())){
                return team;
            }
        }
        return null;
    }

    public static boolean isTeamEliminated(Player player,Team toCheck){
        for (Team team : playerTeamsMap.get(player.getUniqueId())){
            if (team.equals(toCheck)){
                return false;
            }
        }
        return true;
    }

    public static void updateTeams(UHCRun uhcRun){
        if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)) {
            for (Player player : uhcRun.getPlayers()) {
                List<Team> teamList = playerTeamsMap.get(player.getUniqueId());
                teamList.removeIf(team -> team.getSize() == 0);
            }
        }
    }

    public static boolean oneTeamRemaining(Player player){
        return playerTeamsMap.get(player.getUniqueId()).size() == 1;
    }

    public static Team getLastTeam(Player player){
        if (oneTeamRemaining(player)){
            return playerTeamsMap.get(player.getUniqueId()).get(0);
        } else {
            return null;
        }
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
