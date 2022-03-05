package fr.timeuh.uhcrun.teamlists;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerTeam {
    public static Scoreboard scoreBoard = Bukkit.getScoreboardManager().getMainScoreboard();
    public static Team redTeam = scoreBoard.registerNewTeam("redTeam");

    public static void setPlayerTeam(Player player, String team){
        switch(team){
            case "RED":
                redTeam.addEntry(player.getName());
                player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.RED + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.RED + "ROUGE");
                break;
            case "BLUE":

                player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.BLUE + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.BLUE + "BLEUE");
                break;
            case "GREEN":

                player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.GREEN + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.GREEN + "VERTE");
                break;
            default:
                break;
        }
    }

    public static void setupRedTeam(){
        redTeam.setPrefix(ChatColor.RED + "[ROUGE]" + ChatColor.RED);
    }
}
