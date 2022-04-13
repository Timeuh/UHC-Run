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

        Team redTeam = board.registerNewTeam("RED");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
        playerTeamList.add(redTeam);

        Team blueTeam = board.registerNewTeam("BLUE");
        blueTeam.setPrefix(ChatColor.BLUE + "");
        playerTeamList.add(blueTeam);

        Team orangeTeam = board.registerNewTeam("ORANGE");
        orangeTeam.setPrefix(ChatColor.GOLD + "");
        playerTeamList.add(orangeTeam);

        Team greenTeam = board.registerNewTeam("GREEN");
        greenTeam.setPrefix(ChatColor.DARK_GREEN + "");
        playerTeamList.add(greenTeam);

        Team pinkTeam = board.registerNewTeam("PINK");
        pinkTeam.setPrefix(ChatColor.LIGHT_PURPLE + "");
        playerTeamList.add(pinkTeam);

        Team purpleTeam = board.registerNewTeam("PURPLE");
        purpleTeam.setPrefix(ChatColor.DARK_PURPLE + "");
        playerTeamList.add(purpleTeam);

        Team yellowTeam = board.registerNewTeam("YELLOW");
        yellowTeam.setPrefix(ChatColor.YELLOW + "");
        playerTeamList.add(yellowTeam);

        Team grayTeam = board.registerNewTeam("GRAY");
        grayTeam.setPrefix(ChatColor.DARK_GRAY + "");
        playerTeamList.add(grayTeam);

        Team blackTeam = board.registerNewTeam("BLACK");
        blackTeam.setPrefix(ChatColor.BLACK + "");
        playerTeamList.add(blackTeam);

        player.setScoreboard(board);
        playerTeamsMap.put(player.getUniqueId(), playerTeamList);
    }

    public static void rejoinScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        List<Team> playerTeamList = new ArrayList<>();
        board.registerNewObjective("UHCRunLobby","dummy");
        board.registerNewObjective("UHCRunPVP","dummy");
        board.registerNewObjective("UHCRun","dummy");

        Team redTeam = board.registerNewTeam("RED");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
        playerTeamList.add(redTeam);

        Team blueTeam = board.registerNewTeam("BLUE");
        blueTeam.setPrefix(ChatColor.BLUE + "");
        playerTeamList.add(blueTeam);

        Team orangeTeam = board.registerNewTeam("ORANGE");
        orangeTeam.setPrefix(ChatColor.GOLD + "");
        playerTeamList.add(orangeTeam);

        Team greenTeam = board.registerNewTeam("GREEN");
        greenTeam.setPrefix(ChatColor.DARK_GREEN + "");
        playerTeamList.add(greenTeam);

        Team pinkTeam = board.registerNewTeam("PINK");
        pinkTeam.setPrefix(ChatColor.LIGHT_PURPLE + "");
        playerTeamList.add(pinkTeam);

        Team purpleTeam = board.registerNewTeam("PURPLE");
        purpleTeam.setPrefix(ChatColor.DARK_PURPLE + "");
        playerTeamList.add(purpleTeam);

        Team yellowTeam = board.registerNewTeam("YELLOW");
        yellowTeam.setPrefix(ChatColor.YELLOW + "");
        playerTeamList.add(yellowTeam);

        Team grayTeam = board.registerNewTeam("GRAY");
        grayTeam.setPrefix(ChatColor.DARK_GRAY + "");
        playerTeamList.add(grayTeam);

        Team blackTeam = board.registerNewTeam("BLACK");
        blackTeam.setPrefix(ChatColor.BLACK + "");
        playerTeamList.add(blackTeam);

        player.setScoreboard(board);
    }

    public static void updateScoreboard(UHCRun uhcRun){
        for (Player player : uhcRun.getActualPlayers()) {
            if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)) {
                if (hasTeam(player)) {
                    String playerTeamName = getPlayerTeam(player).getName();
                    switch (playerTeamName) {
                        case "RED":
                            rejoinTeam(player, "RED", uhcRun);
                            break;

                        case "BLUE":
                            rejoinTeam(player, "BLUE", uhcRun);
                            break;

                        case "ORANGE":
                            rejoinTeam(player, "ORANGE", uhcRun);
                            break;

                        case "GREEN":
                            rejoinTeam(player, "GREEN", uhcRun);
                            break;

                        case "PINK":
                            rejoinTeam(player, "PINK", uhcRun);
                            break;

                        case "PURPLE":
                            rejoinTeam(player, "PURPLE", uhcRun);
                            break;

                        case "YELLOW":
                            rejoinTeam(player, "YELLOW", uhcRun);
                            break;

                        case "GRAY":
                            rejoinTeam(player, "GRAY", uhcRun);
                            break;

                        case "BLACK":
                            rejoinTeam(player, "BLACK", uhcRun);
                            break;

                        default:
                            break;
                    }
                }
            }
            uhcRun.createLobbyBoard(player);
        }
    }

    public static void rejoinTeam(Player player, String team, UHCRun uhcRun) {
        for (Player sbPlayer : uhcRun.getActualPlayers()) {
            Scoreboard board = sbPlayer.getScoreboard();
            switch (team) {
                case "RED":
                    Team redTeam = board.getTeam("RED");
                    redTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_RED + player.getName());
                    break;

                case "BLUE":
                    Team blueTeam = board.getTeam("BLUE");
                    blueTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
                    break;

                case "ORANGE":
                    Team orangeTeam = board.getTeam("ORANGE");
                    orangeTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.GOLD + player.getName());
                    break;

                case "GREEN":
                    Team greenTeam = board.getTeam("GREEN");
                    greenTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GREEN + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GREEN + player.getName());
                    break;

                case "PINK":
                    Team pinkTeam = board.getTeam("PINK");
                    pinkTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
                    break;

                case "PURPLE":
                    Team purpleTeam = board.getTeam("PURPLE");
                    purpleTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
                    break;

                case "YELLOW":
                    Team yellowTeam = board.getTeam("YELLOW");
                    yellowTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.YELLOW + player.getName());
                    break;

                case "GRAY":
                    Team grayTeam = board.getTeam("GRAY");
                    grayTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GRAY + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GRAY + player.getName());
                    break;

                case "BLACK":
                    Team blackTeam = board.getTeam("BLACK");
                    blackTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLACK + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLACK + player.getName());
                    break;

                default:
                    player.setDisplayName(ChatColor.WHITE + "[Pas de Team] " + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.WHITE + "[Pas de Team] " + player.getName());
                    break;
            }
        }
    }

    public static void joinTeam(Player player, String team, UHCRun uhcRun){
        for (Player sbPlayer : uhcRun.getActualPlayers()) {
            Scoreboard board = sbPlayer.getScoreboard();
            switch (team) {
                case "RED":
                    Team redTeam = board.getTeam("RED");
                    redTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_RED + player.getName());
                    break;

                case "BLUE":
                    Team blueTeam = board.getTeam("BLUE");
                    blueTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLUE + player.getName());
                    break;

                case "ORANGE":
                    Team orangeTeam = board.getTeam("ORANGE");
                    orangeTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.GOLD + player.getName());
                    break;

                case "GREEN":
                    Team greenTeam = board.getTeam("GREEN");
                    greenTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GREEN + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GREEN + player.getName());
                    break;

                case "PINK":
                    Team pinkTeam = board.getTeam("PINK");
                    pinkTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
                    break;

                case "PURPLE":
                    Team purpleTeam = board.getTeam("PURPLE");
                    purpleTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
                    break;

                case "YELLOW":
                    Team yellowTeam = board.getTeam("YELLOW");
                    yellowTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.YELLOW + player.getName());
                    break;

                case "GRAY":
                    Team grayTeam = board.getTeam("GRAY");
                    grayTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.DARK_GRAY + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.DARK_GRAY + player.getName());
                    break;

                case "BLACK":
                    Team blackTeam = board.getTeam("BLACK");
                    blackTeam.addEntry(player.getName());
                    player.setDisplayName(ChatColor.BLACK + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.BLACK + player.getName());
                    break;

                default:
                    player.setDisplayName(ChatColor.WHITE + "[Pas de Team] " + player.getName() + ChatColor.WHITE);
                    player.setPlayerListName(ChatColor.WHITE + "[Pas de Team] " + player.getName());
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
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous n'avez pas pu rejoindre d'équipe à cause d'un bug, reportez-le à Timeuh#2670 sur discord");
                break;
        }
    }



    public static void leaveTeam(Player player, UHCRun uhcRun){
        for (Player sbPlayer : uhcRun.getActualPlayers()) {
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
        for (Player sbPlayer : uhcRun.getActualPlayers()) {
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
        for (Player player : uhcRun.getActualPlayers()){
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
            for (Player player : uhcRun.getActualPlayers()) {
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
            case "RED":
                return ChatColor.DARK_RED;
            case "BLUE":
                return ChatColor.BLUE;
            case "ORANGE":
                return ChatColor.GOLD;
            case "GREEN":
                return ChatColor.DARK_GREEN;
            case "PINK":
                return ChatColor.LIGHT_PURPLE;
            case "PURPLE":
                return ChatColor.DARK_PURPLE;
            case "YELLOW":
                return ChatColor.YELLOW;
            case "GRAY":
                return ChatColor.DARK_GRAY;
            case "BLACK":
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
