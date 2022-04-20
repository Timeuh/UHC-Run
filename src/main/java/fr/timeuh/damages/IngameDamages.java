package fr.timeuh.damages;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import fr.timeuh.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class IngameDamages implements Listener {
    private UHCRun uhcRun;

    public IngameDamages(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void pvp(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getDamager() instanceof Player){
                Player attacker = (Player) event.getDamager();
                if ((uhcRun.isState(State.PLAYING) || uhcRun.isState(State.PVP)) && !uhcRun.getPlayers().containsInvinciblePlayer(player.getUniqueId())) {
                    //scenario friendly fire
                }
            }
        }
    }

    @EventHandler
    public void damageCancel(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if ((uhcRun.isState(State.PLAYING) || uhcRun.isState(State.PVP)) && uhcRun.getPlayers().containsInvinciblePlayer(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void kill(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer != null) killer.setStatistic(Statistic.PLAYER_KILLS, killer.getStatistic(Statistic.PLAYER_KILLS) + 1);
        victim.sendMessage(ChatColor.GOLD + "Vous êtes mort, CHEH !");
        uhcRun.getPlayers().removeLivePlayer(victim.getUniqueId());
        if (uhcRun.getScenario().isEnabled(Scenario.TEAMS)){
            ChatColor teamColor = uhcRun.getTeams().getTeamColor(uhcRun.getTeams().getPlayerTeam(victim));
            uhcRun.getTeams().eliminatePlayer(victim);
            event.setDeathMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + teamColor + victim.getName() + ChatColor.GOLD  + " est mort");
        } else {
            event.setDeathMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + victim.getName() + ChatColor.GOLD  + " est mort");
        }
        uhcRun.getWins().checkWin();
        for (Player player : uhcRun.getPlayers().allCoPlayers()) uhcRun.getBoard().displayPvp(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(uhcRun, () -> victim.spigot().respawn());
        victim.setGameMode(GameMode.SPECTATOR);
    }
}
