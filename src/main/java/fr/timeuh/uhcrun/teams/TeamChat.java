package fr.timeuh.uhcrun.teams;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.scenarios.Scenarios;
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
    public void onChat(AsyncPlayerChatEvent event){
        if (uhcRun.checkEnabledScenario(Scenarios.TEAMS)){
            Player sender = event.getPlayer();
            String message = event.getMessage();
            if (PlayerTeams.getPlayerTeam(sender) == null){
                event.setMessage(message);
            } else {
                ChatColor teamColor = PlayerTeams.getTeamColor(PlayerTeams.getPlayerTeam(sender));
                if (!message.startsWith("!")) {
                    for (String msgPlayer : PlayerTeams.getPlayerTeam(sender).getEntries()) {
                        Bukkit.getPlayer(msgPlayer).sendMessage(teamColor + "[TEAM] " + ChatColor.WHITE + "<" + teamColor + sender.getName() + ChatColor.WHITE + "> " + ChatColor.WHITE + message);
                        event.setCancelled(true);
                    }
                } else {
                    String[] messageParts = message.split("!");
                    event.setMessage(messageParts[1]);
                }
            }
        }
    }
}
