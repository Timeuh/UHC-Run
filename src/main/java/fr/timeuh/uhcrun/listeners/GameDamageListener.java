package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
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

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && uhcRun.getNoFallPlayers().contains(player)){
                uhcRun.supprimerNoFall(player);
                player.setFallDistance(0f);
                event.setDamage(0);
                event.setCancelled(true);
            } else if (player.getHealth() <= event.getDamage()){
                if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                Bukkit.broadcastMessage("§5[UHCRun] §4" +player.getName()+ " §6 est mort");
                uhcRun.eliminate(player);
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
                uhcRun.eliminate(player);
                Bukkit.broadcastMessage("§5[UHCRun] §4" +player.getName()+ " §6 est mort");
                if (killer instanceof Player){
                    Player killerPlayer = (Player) killer;
                    killerPlayer.setStatistic(Statistic.PLAYER_KILLS, killerPlayer.getStatistic(Statistic.PLAYER_KILLS)+1);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
    }
}
