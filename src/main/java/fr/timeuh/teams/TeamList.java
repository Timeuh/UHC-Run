package fr.timeuh.teams;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamList {

    private UHCRun uhcRun;
    private HashMap<UUID, List<Team>> playerTeamList;

    public TeamList(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.playerTeamList = new HashMap<>();
    }

    public void joinTeamBoard(Player player){
        Scoreboard board = player.getScoreboard();
        List<Team> teamList = new ArrayList<>();

        Team redTeam = board.registerNewTeam("Rouge");
        redTeam.setPrefix(ChatColor.DARK_RED + "");
        teamList.add(redTeam);

        Team blueTeam = board.registerNewTeam("Bleue");
        blueTeam.setPrefix(ChatColor.BLUE + "");
        teamList.add(blueTeam);

        Team orangeTeam = board.registerNewTeam("Orange");
        orangeTeam.setPrefix(ChatColor.GOLD + "");
        teamList.add(orangeTeam);

        Team greenTeam = board.registerNewTeam("Verte");
        greenTeam.setPrefix(ChatColor.DARK_GREEN + "");
        teamList.add(greenTeam);

        Team pinkTeam = board.registerNewTeam("Rose");
        pinkTeam.setPrefix(ChatColor.LIGHT_PURPLE + "");
        teamList.add(pinkTeam);

        Team purpleTeam = board.registerNewTeam("Violette");
        purpleTeam.setPrefix(ChatColor.DARK_PURPLE + "");
        teamList.add(purpleTeam);

        Team yellowTeam = board.registerNewTeam("Jaune");
        yellowTeam.setPrefix(ChatColor.YELLOW + "");
        teamList.add(yellowTeam);

        Team grayTeam = board.registerNewTeam("Grise");
        grayTeam.setPrefix(ChatColor.DARK_GRAY + "");
        teamList.add(grayTeam);

        Team blackTeam = board.registerNewTeam("Noire");
        blackTeam.setPrefix(ChatColor.BLACK + "");
        teamList.add(blackTeam);

        playerTeamList.put(player.getUniqueId(), teamList);
        player.setScoreboard(board);
    }

    public void joinTeam(Player player, String team){
        ChatColor teamColor = ChatColor.WHITE;
        for (Player sbPlayer : uhcRun.getPlayers().allCoPlayers()) {
            Team joinTeam = sbPlayer.getScoreboard().getTeam(team);
            if (joinTeam != null) {
                joinTeam.addEntry(player.getName());

                switch (team) {
                    case "Rouge":
                        player.setDisplayName(ChatColor.DARK_RED + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.DARK_RED + player.getName());
                        teamColor = ChatColor.DARK_RED;
                        break;

                    case "Bleue":
                        player.setDisplayName(ChatColor.DARK_BLUE + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.DARK_BLUE + player.getName());
                        teamColor = ChatColor.DARK_BLUE;
                        break;

                    case "Orange":
                        player.setDisplayName(ChatColor.GOLD + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.GOLD + player.getName());
                        teamColor = ChatColor.GOLD;
                        break;

                    case "Verte":
                        player.setDisplayName(ChatColor.DARK_GREEN + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.DARK_GREEN + player.getName());
                        teamColor = ChatColor.DARK_GREEN;
                        break;

                    case "Rose":
                        player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
                        teamColor = ChatColor.LIGHT_PURPLE;
                        break;

                    case "Violette":
                        player.setDisplayName(ChatColor.DARK_PURPLE + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
                        teamColor = ChatColor.DARK_PURPLE;
                        break;

                    case "Jaune":
                        player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.YELLOW + player.getName());
                        teamColor = ChatColor.YELLOW;
                        break;

                    case "Grise":
                        player.setDisplayName(ChatColor.GRAY + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.GRAY + player.getName());
                        teamColor = ChatColor.GRAY;
                        break;

                    case "Noire":
                        player.setDisplayName(ChatColor.BLACK + player.getName() + ChatColor.WHITE);
                        player.setPlayerListName(ChatColor.BLACK + player.getName());
                        teamColor = ChatColor.BLACK;
                        break;
                }
            }
        }
        if (uhcRun.isState(State.WAITING)) player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Vous rejoingez l'équipe " + teamColor + team);
    }

    public void leaveTeam(Player player){
        Team playerTeam = getPlayerTeam(player);
        if (playerTeam != null){
            for (Player sbPlayer : uhcRun.getPlayers().allCoPlayers()){
                for (Team team : sbPlayer.getScoreboard().getTeams()){
                    if (team.equals(playerTeam)) team.removeEntry(player.getName());
                }
            }
        }
    }

    public Team getPlayerTeam(Player player){
        for (Team team : player.getScoreboard().getTeams()){
            if (team.getEntries().contains(player.getName())){
                return team;
            }
        }
        return null;
    }

    public boolean allPlayersInTeam(){
        for (Player player : uhcRun.getPlayers().allCoPlayers()){
            if (getPlayerTeam(player) == null) return false;
        }
        return true;
    }

    public boolean oneTeamRemaining(){
        Player player = getAnActualPlayer();
        if (player != null){
            List<Team> teams = playerTeamList.get(player.getUniqueId());
            return teams.size() == 1;
        }
        return false;
    }

    public Player getAnActualPlayer(){
        for (Player player : uhcRun.getPlayers().allLivePlayers()){
            if (player != null) return player;
        }
        return null;
    }

    public Team getWinnerTeam(){
        Player player = getAnActualPlayer();
        List<Team> teams = playerTeamList.get(player.getUniqueId());
        return teams.get(0);
    }

    public ChatColor getTeamColor(Team team){
        switch (team.getName()){
            case "Rouge":
                return ChatColor.DARK_RED;
            case "Bleue":
                return ChatColor.DARK_BLUE;
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
                return ChatColor.GRAY;
            case "Noire":
                return ChatColor.BLACK;
            default:
                return ChatColor.WHITE;
        }
    }

    public void eliminatePlayer(Player player){
        Team team = getPlayerTeam(player);
        if (team != null){
            for (Player sbPlayer : uhcRun.getPlayers().allLivePlayers()){
                for (Team playerTeams : sbPlayer.getScoreboard().getTeams()){
                    if (playerTeams.equals(team)) playerTeams.removeEntry(player.getName());
                }
                updateTeams(sbPlayer);
            }
        }
    }

    public void updateTeams(Player player){
        List<Team> teams = playerTeamList.get(player.getUniqueId());
        teams.removeIf(team -> team.getSize() == 0);
    }


}
