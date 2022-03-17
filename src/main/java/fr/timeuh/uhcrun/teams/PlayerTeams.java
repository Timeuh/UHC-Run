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

        Team orangeTeam = board.registerNewTeam("Orange");
        orangeTeam.setPrefix(ChatColor.GOLD + "");
        playerTeamList.add(orangeTeam);

        Team greenTeam = board.registerNewTeam("Verte");
        greenTeam.setPrefix(ChatColor.DARK_GREEN + "");
        playerTeamList.add(greenTeam);

        Team pinkTeam = board.registerNewTeam("Rose");
        pinkTeam.setPrefix(ChatColor.LIGHT_PURPLE + "");
        playerTeamList.add(pinkTeam);

        Team purpleTeam = board.registerNewTeam("Violette");
        purpleTeam.setPrefix(ChatColor.DARK_PURPLE + "");
        playerTeamList.add(purpleTeam);

        Team yellowTeam = board.registerNewTeam("Jaune");
        yellowTeam.setPrefix(ChatColor.YELLOW + "");
        playerTeamList.add(yellowTeam);

        Team grayTeam = board.registerNewTeam("Grise");
        grayTeam.setPrefix(ChatColor.DARK_GRAY + "");
        playerTeamList.add(grayTeam);

        Team blackTeam = board.registerNewTeam("Noire");
        blackTeam.setPrefix(ChatColor.BLACK + "");
        playerTeamList.add(blackTeam);

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

                        case "Orange":
                            joinTeam(player, "ORANGE", uhcRun);
                            break;

                        case "Verte":
                            joinTeam(player, "GREEN", uhcRun);
                            break;

                        case "Rose":
                            joinTeam(player, "PINK", uhcRun);
                            break;

                        case "Violette":
                            joinTeam(player, "PURPLE", uhcRun);
                            break;

                        case "Jaune":
                            joinTeam(player, "YELLOW", uhcRun);
                            break;

                        case "Grise":
                            joinTeam(player, "GRAY", uhcRun);
                            break;

                        case "Noire":
                            joinTeam(player, "BLACK", uhcRun);
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

                case "ORANGE":
                    Team orangeTeam = board.getTeam("Orange");
                    orangeTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.GOLD + player.getName());
                    break;

                case "GREEN":
                    Team greenTeam = board.getTeam("Verte");
                    greenTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GREEN + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GREEN + player.getName());
                    break;

                case "PINK":
                    Team pinkTeam = board.getTeam("Rose");
                    pinkTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
                    break;

                case "PURPLE":
                    Team purpleTeam = board.getTeam("Violette");
                    purpleTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
                    break;

                case "YELLOW":
                    Team yellowTeam = board.getTeam("Jaune");
                    yellowTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.YELLOW + player.getName());
                    break;

                case "GRAY":
                    Team grayTeam = board.getTeam("Grise");
                    grayTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GRAY + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GRAY + player.getName());
                    break;

                case "BLACK":
                    Team blackTeam = board.getTeam("Noire");
                    blackTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLACK + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLACK + player.getName());
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

            case "ORANGE":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe ORANGE");
                break;

            case "GREEN":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.DARK_GREEN + "VERTE");
                break;

            case "PINK":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.LIGHT_PURPLE + "ROSE");
                break;

            case "PURPLE":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.DARK_PURPLE + "VIOLETTE");
                break;

            case "YELLOW":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.YELLOW + "JAUNE");
                break;

            case "GRAY":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.DARK_GRAY + "GRISE");
                break;

            case "BLACK":
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'équipe " + ChatColor.BLACK + "NOIRE");
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

    public static void leaveTeamIngame(Player player, UHCRun uhcRun){
        for (Player sbPlayer : uhcRun.getPlayers()) {
            List<Team> teamList = playerTeamsMap.get(sbPlayer.getUniqueId());
            for (Team team : teamList ) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                    player.setDisplayName(ChatColor.WHITE + "[MORT] " + player.getName());
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
            case "Orange":
                return ChatColor.GOLD;
            case "Verte":
                return ChatColor.DARK_GREEN;
            case "Rose":
                return ChatColor.LIGHT_PURPLE;
            case "Violette":
                return ChatColor.DARK_PURPLE;
            case "Jaune":
                return ChatColor.YELLOW;
            case "Grise":
                return ChatColor.DARK_GRAY;
            case "Noire":
                return ChatColor.BLACK;
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
                metaRedWool.setDisplayName(ChatColor.DARK_RED + "Équipe rouge");
                redWool.setItemMeta(metaRedWool);
                return redWool;

            case "BLUE":
                ItemStack blueWool = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
                ItemMeta metaBlueWool = blueWool.getItemMeta();
                metaBlueWool.setDisplayName(ChatColor.BLUE + "Équipe bleue");
                blueWool.setItemMeta(metaBlueWool);
                return blueWool;

            case "ORANGE":
                ItemStack orangeWool = new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getData());
                ItemMeta metaOrangeWool = orangeWool.getItemMeta();
                metaOrangeWool.setDisplayName(ChatColor.GOLD + "Équipe orange");
                orangeWool.setItemMeta(metaOrangeWool);
                return orangeWool;

            case "GREEN":
                ItemStack greenWool = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
                ItemMeta metaGreenWool = greenWool.getItemMeta();
                metaGreenWool.setDisplayName(ChatColor.DARK_GREEN + "Équipe verte");
                greenWool.setItemMeta(metaGreenWool);
                return greenWool;

            case "PINK":
                ItemStack pinkWool = new ItemStack(Material.WOOL, 1, DyeColor.PINK.getData());
                ItemMeta metaPinkWool = pinkWool.getItemMeta();
                metaPinkWool.setDisplayName(ChatColor.LIGHT_PURPLE + "Équipe rose");
                pinkWool.setItemMeta(metaPinkWool);
                return pinkWool;

            case "PURPLE":
                ItemStack purpleWool = new ItemStack(Material.WOOL, 1, DyeColor.PURPLE.getData());
                ItemMeta metaPurpleWool = purpleWool.getItemMeta();
                metaPurpleWool.setDisplayName(ChatColor.DARK_PURPLE + "Équipe violette");
                purpleWool.setItemMeta(metaPurpleWool);
                return purpleWool;

            case "YELLOW":
                ItemStack yellowWool = new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData());
                ItemMeta metaYellowWool = yellowWool.getItemMeta();
                metaYellowWool.setDisplayName(ChatColor.YELLOW + "Équipe jaune");
                yellowWool.setItemMeta(metaYellowWool);
                return yellowWool;

            case "GRAY":
                ItemStack grayWool = new ItemStack(Material.WOOL, 1, DyeColor.GRAY.getData());
                ItemMeta metaGrayWool = grayWool.getItemMeta();
                metaGrayWool.setDisplayName(ChatColor.DARK_GRAY + "Équipe grise");
                grayWool.setItemMeta(metaGrayWool);
                return grayWool;

            case "BLACK":
                ItemStack blackWool = new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getData());
                ItemMeta metaBlackWool = blackWool.getItemMeta();
                metaBlackWool.setDisplayName(ChatColor.BLACK + "Équipe noire");
                blackWool.setItemMeta(metaBlackWool);
                return blackWool;

            default:
                return null;
        }
    }
}
