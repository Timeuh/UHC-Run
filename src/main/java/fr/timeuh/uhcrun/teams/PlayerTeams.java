package fr.timeuh.uhcrun.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class PlayerTeams {
    public Scoreboard board;

    public PlayerTeams(){
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();

        Team redTeam = board.registerNewTeam("redTeam");
        redTeam.setPrefix(ChatColor.RED + "");
    }

    public void joinTeam(Player player, String team){
        if (team.equals("RED")){
            Team join = board.getTeam("redTeam");
            join.addEntry(player.getName());
            player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
            player.setPlayerListName(ChatColor.RED + player.getName());
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun]" + ChatColor.GOLD + " Vous venez de rejoindre l'equipe " + ChatColor.RED + "ROUGE");
        }
    }

    public void joinScoreboard(Player player){
        player.setScoreboard(board);
    }
}
