package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.scenarios.Scenarios;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameDamageListener implements Listener {

    private UHCRun uhcRun;
    private PlayerTeams teams;

    public GameDamageListener(UHCRun uhcRun, PlayerTeams teams) {
        this.uhcRun = uhcRun;
        this.teams = teams;
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity victim = event.getEntity();
        if (victim instanceof Player){
            Player player = (Player) victim;

            if (uhcRun.getCancelDamagePlayers().contains(player)){
                event.setCancelled(true);
            } else if (player.getHealth() <= event.getDamage()){
                if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " est mort");
                uhcRun.eliminatePlayer(player, teams);

                if (uhcRun.isState(GameState.FIGHTING)) {
                    for (Player sbPlayer : uhcRun.getPlayers()) {
                        uhcRun.createPVPBoard(sbPlayer, teams);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event){
        Entity victim = event.getEntity();
        Entity killer = event.getDamager();
        if (victim instanceof Player){
            Player player = (Player) victim;
            if (player.getHealth() <= event.getDamage()){
                uhcRun.eliminatePlayer(player, teams);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " est mort");
                if (killer instanceof Player){
                    Player killerPlayer = (Player) killer;
                    killerPlayer.setStatistic(Statistic.PLAYER_KILLS, killerPlayer.getStatistic(Statistic.PLAYER_KILLS)+1);
                }
                if (uhcRun.isState(GameState.FIGHTING)) {
                    for (Player sbPlayer : uhcRun.getPlayers()) {
                        uhcRun.createPVPBoard(sbPlayer, teams);
                    }
                }
            }
            if (uhcRun.checkEnabledScenario(Scenarios.TEAMS) && !uhcRun.checkEnabledScenario(Scenarios.FRIENDLYFIRE)){
                if (killer instanceof Player){
                    Player killerPlayer = (Player) killer;
                    if (teams.getPlayerTeam(player).equals(teams.getPlayerTeam(killerPlayer)) || uhcRun.isState(GameState.WAITING)){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
    }
}
