package fr.timeuh.teams;

import fr.timeuh.UHCRun;
import fr.timeuh.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TeamChat implements Listener {
    private UHCRun uhcRun;

    public TeamChat(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event){
        if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)) {
            String message = event.getMessage();
            Player player = event.getPlayer();

            if (message.startsWith("!")){
                String[] global = message.split("!");
                event.setMessage(global[1]);
            } else {
                TeamList teams = uhcRun.getTeams();
                ChatColor color = teams.getTeamColor(teams.getPlayerTeam(player));
                for (String msgPlayer : teams.getPlayerTeam(player).getEntries()){
                    Bukkit.getPlayer(msgPlayer).sendMessage(color + "[TEAM] " + ChatColor.WHITE + "<" + color + msgPlayer + ChatColor.WHITE + "> " + message);
                    event.setCancelled(true);
                }
            }
        }
    }
}
