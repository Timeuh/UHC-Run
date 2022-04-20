package fr.timeuh.teams;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamList {

    private UHCRun uhcRun;

    public TeamList(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    public void joinTeamBoard(Player player){
        Scoreboard board = player.getScoreboard();

        Team redTeam = board.registerNewTeam("Rouge");
        redTeam.setPrefix(ChatColor.DARK_RED + "");

        Team blueTeam = board.registerNewTeam("Bleue");
        blueTeam.setPrefix(ChatColor.BLUE + "");

        Team orangeTeam = board.registerNewTeam("Orange");
        orangeTeam.setPrefix(ChatColor.GOLD + "");

        Team greenTeam = board.registerNewTeam("Verte");
        greenTeam.setPrefix(ChatColor.DARK_GREEN + "");

        Team pinkTeam = board.registerNewTeam("Rose");
        pinkTeam.setPrefix(ChatColor.LIGHT_PURPLE + "");

        Team purpleTeam = board.registerNewTeam("Violette");
        purpleTeam.setPrefix(ChatColor.DARK_PURPLE + "");

        Team yellowTeam = board.registerNewTeam("Jaune");
        yellowTeam.setPrefix(ChatColor.YELLOW + "");

        Team grayTeam = board.registerNewTeam("Grise");
        grayTeam.setPrefix(ChatColor.DARK_GRAY + "");

        Team blackTeam = board.registerNewTeam("Noire");
        blackTeam.setPrefix(ChatColor.BLACK + "");

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


}
