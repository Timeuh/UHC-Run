package fr.timeuh.uhcrun.listeners;

import fr.timeuh.uhcrun.UHCRun;
import org.bukkit.Bukkit;
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
                Bukkit.broadcastMessage("§5[UHCRun] §4" +player.getName()+ " §6 est mort");
                uhcRun.eliminate(player);

                for (Player sbPlayer : uhcRun.getPlayers()){
                    uhcRun.createBoard(sbPlayer);
                }
            }
        }

    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event){
        Entity victim = event.getEntity();
        if (victim instanceof Player){
            Player player = (Player) victim;
            if (player.getHealth() <= event.getDamage()){
                uhcRun.eliminate(player);
                Bukkit.broadcastMessage("§5[UHCRun] §4" +player.getName()+ " §6 est mort");
            }

            for (Player sbPlayer : uhcRun.getPlayers()){
                uhcRun.createBoard(sbPlayer);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
    }
}
