package fr.timeuh.uhcrun.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeams {
    public static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    public static void createPlayerTeams(){
        Objective obj = board.registerNewObjective("playerList", "dummy");
        obj.setDisplayName(ChatColor.RED + "PlayerList");
        obj.setDisplaySlot(DisplaySlot.BELOW_NAME);

        Team redTeam = board.registerNewTeam("redTeam");
        redTeam.setPrefix(ChatColor.RED + "[ROUGE]");
    }

    public static void joinTeam(Player player, String team){
        if (team.equals("RED")){
            Team join = board.getTeam("redTeam");
            join.addEntry(player.getName());
            player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
            player.setPlayerListName(ChatColor.RED + player.getName());
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'equipe " + ChatColor.RED + "ROUGE");
        }
    }
}
