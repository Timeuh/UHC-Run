package fr.timeuh.uhcrun.teamlists;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerTeam {
    public static ArrayList<String> teamRed = new ArrayList<>();
    public static ArrayList<String> teamBlue = new ArrayList<>();
    public static ArrayList<String> teamGreen = new ArrayList<>();

    public static void setPlayerTeam(Player player, TeamList team){
        switch(team){
            case RED:
                teamRed.add(player.getName());
                player.setDisplayName(ChatColor.RED + player.getName());
                player.setPlayerListName(ChatColor.RED + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.RED + "ROUGE");
                break;
            case BLUE:
                teamBlue.add(player.getName());
                player.setDisplayName(ChatColor.BLUE + player.getName());
                player.setPlayerListName(ChatColor.BLUE + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.BLUE + "BLEUE");
                break;
            case GREEN:
                teamGreen.add(player.getName());
                player.setDisplayName(ChatColor.GREEN + player.getName());
                player.setPlayerListName(ChatColor.GREEN + player.getName());
                player.sendMessage("§5[UHCRun] §6Vous avez choisi l'equipe " + ChatColor.GREEN + "VERTE");
                break;
            default:
                break;
        }
    }
}
