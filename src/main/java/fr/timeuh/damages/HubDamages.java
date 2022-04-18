package fr.timeuh.damages;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HubDamages implements Listener {

    private UHCRun uhcRun;

    public HubDamages(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void pvpHub(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.STARTING) || uhcRun.isState(State.FINISH) || uhcRun.getPlayers().containsInvinciblePlayer(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damageHub(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.STARTING) || uhcRun.isState(State.FINISH) || uhcRun.getPlayers().containsInvinciblePlayer(player.getUniqueId())){
                event.setCancelled(true);
            }
        }
    }
}

