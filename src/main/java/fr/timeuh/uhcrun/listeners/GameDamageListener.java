package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.GameState;
import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.entity.Arrow;
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
        Entity victime = event.getEntity();

        if (!uhcRun.isState(GameState.PLAYING)){
            event.setCancelled(true);
            return;
        }

        if (victime instanceof Player){
            Player player = (Player) victime;
            if (player.getHealth() <= event.getDamage()){
                uhcRun.eliminate(player);
            }
        }
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event){
        Entity victim = event.getEntity();
        if (victim instanceof Player){
            Player player = (Player) victim;
            Entity damager = event.getDamager();
            Player killer = player;

            if (player.getHealth() <= event.getDamage()){

                if (damager instanceof Player){
                    killer = (Player) damager;
                }

                if (damager instanceof Arrow){
                    Arrow arrow = (Arrow) damager;
                    if (arrow.getShooter() instanceof Player){
                        killer = (Player) arrow.getShooter();
                    }
                }
                killer.sendMessage("Vous avez tué " +player.getName());
                uhcRun.eliminate(player);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
    }
}
