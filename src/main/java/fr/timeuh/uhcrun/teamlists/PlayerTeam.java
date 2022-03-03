package fr.timeuh.uhcrun.teamlists;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerTeam {
    public static List<String> teamRed = new ArrayList<>();
    public static List<String> teamBlue = new ArrayList<>();
    public static List<String> teamGreen = new ArrayList<>();

    public static void setPlayerTeam(Player player, TeamList team){
        switch(team){
            case RED:
                teamRed.add(player.getName());
                player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.RED + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.RED + "ROUGE");
                break;
            case BLUE:
                teamBlue.add(player.getName());
                player.setDisplayName(ChatColor.BLUE + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.BLUE + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.BLUE + "BLEUE");
                break;
            case GREEN:
                teamGreen.add(player.getName());
                player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.WHITE);
                player.setPlayerListName(ChatColor.GREEN + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.GREEN + "VERTE");
                break;
            default:
                break;
        }
    }
}
