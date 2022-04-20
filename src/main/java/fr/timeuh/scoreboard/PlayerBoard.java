package fr.timeuh.scoreboard;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class PlayerBoard {

    private UHCRun uhcRun;
    private HashMap<UUID, Scoreboard> playerSb;
    private final ChatColor gold = ChatColor.GOLD;
    private final ChatColor red = ChatColor.DARK_RED;

    public PlayerBoard(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
        this.playerSb = new HashMap<>();
    }

    public void joinScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewObjective("Lobby", "dummy");
        board.registerNewObjective("Run", "dummy");
        board.registerNewObjective("PVP", "dummy");
        playerSb.put(player.getUniqueId(), board);
        player.setScoreboard(board);
    }

    public void displayLobby(Player player){
        if (player != null) {
            Objective obj = playerSb.get(player.getUniqueId()).getObjective("Lobby");
            obj.unregister();
            obj = playerSb.get(player.getUniqueId()).registerNewObjective("Lobby", "dummy");
            obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun V2" + ChatColor.GOLD + " by " + ChatColor.DARK_RED + "Timeuh");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
            score.setScore(3);
            Score score1 = obj.getScore(ChatColor.GOLD + "Bienvenue dans cet UHC Run !");
            score1.setScore(2);
            Score score2 = obj.getScore(ChatColor.GOLD + "PVP : " + ChatColor.DARK_RED + 20 + ChatColor.GOLD + " minutes");
            score2.setScore(1);
            Score score3 = obj.getScore(ChatColor.GOLD + "Joueurs en ligne : " + ChatColor.DARK_RED + uhcRun.getPlayers().getCoPlayers().size() + ChatColor.GOLD + "/" + ChatColor.GOLD + Bukkit.getMaxPlayers());
            score3.setScore(0);
        }
    }

    public void displayRun(Player player, int timer){
        if (player != null) {
            Objective obj = playerSb.get(player.getUniqueId()).getObjective("Run");
            obj.unregister();
            obj = playerSb.get(player.getUniqueId()).registerNewObjective("Run", "dummy");
            obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun V2" + ChatColor.GOLD + " by " + ChatColor.DARK_RED + "Timeuh");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
            score.setScore(3);
            Score score1 = obj.getScore(ChatColor.GOLD + "Joueurs en vie : " + ChatColor.DARK_RED + uhcRun.getPlayers().getCoPlayers().size());
            score1.setScore(2);
            int seconds = timer % 60;
            int minutes = timer / 60;
            Score score2 = obj.getScore(gold + "Phase PvP dans " + red + minutes + ":" + seconds);
            score2.setScore(1);
        }
    }

    public void displayPvp(Player player){
        if (player != null) {
            Objective obj = playerSb.get(player.getUniqueId()).getObjective("PVP");
            obj.unregister();
            obj = playerSb.get(player.getUniqueId()).registerNewObjective("PVP", "dummy");
            obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun V2" + ChatColor.GOLD + " by " + ChatColor.DARK_RED + "Timeuh");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
            score.setScore(3);
            Score score1 = obj.getScore(ChatColor.GOLD + "Joueurs en vie : " + ChatColor.DARK_RED + uhcRun.getPlayers().getAlivePlayers().size());
            score1.setScore(2);
            Score score3 = obj.getScore(ChatColor.GOLD + "Kills : " + ChatColor.DARK_RED + (player.getStatistic(Statistic.PLAYER_KILLS)));
            score3.setScore(1);
            Score score4 = obj.getScore(ChatColor.GOLD + "Phase PvP " + ChatColor.DARK_RED + "débutée");
            score4.setScore(0);
        }
    }

    public void updateLobby(){
        for (Player player : uhcRun.getPlayers().allCoPlayers()) displayLobby(player);
    }
}
