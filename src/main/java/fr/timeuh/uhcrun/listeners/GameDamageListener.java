package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.scenarios.Scenarios;
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

    public GameDamageListener(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity victim = event.getEntity();
        if (victim instanceof Player){
            Player player = (Player) victim;

            if (uhcRun.getCancelDamagePlayers().contains(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event){
        Entity victim = event.getEntity();
        Entity killer = event.getDamager();
        if (victim instanceof Player){
            Player player = (Player) victim;
            if (uhcRun.checkEnabledScenario(Scenarios.TEAMS) && !uhcRun.isState(GameState.WAITING)){
                if (killer instanceof Player){
                    Player killerPlayer = (Player) killer;
                    String equipeVictime = player.getScoreboard().getEntryTeam(player.getName()).getName();
                    String equipeAttaquant = killerPlayer.getScoreboard().getEntryTeam(killerPlayer.getName()).getName();
                    if (equipeAttaquant.equals(equipeVictime) && !uhcRun.checkEnabledScenario(Scenarios.FRIENDLYFIRE)){
                        event.setCancelled(true);
                    }
                }
            }
            if (uhcRun.isState(GameState.WAITING)){
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
        Player player = event.getEntity();
        Player killer = player.getKiller();
        Bukkit.getScheduler().scheduleSyncDelayedTask(uhcRun, () -> player.spigot().respawn());
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " est mort");
        uhcRun.eliminatePlayer(player);
        killer.setStatistic(Statistic.PLAYER_KILLS, (killer.getStatistic(Statistic.PLAYER_KILLS)) +1);
        for (Player playerSb : uhcRun.getPlayers()){
            uhcRun.createPVPBoard(playerSb);
        }
    }
}
