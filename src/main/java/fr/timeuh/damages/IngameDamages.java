package fr.timeuh.damages;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    public void kill(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        killer.setStatistic(Statistic.PLAYER_KILLS, killer.getStatistic(Statistic.PLAYER_KILLS) + 1);
        victim.sendMessage(ChatColor.GOLD + "Vous êtes mort, CHEH !");
        Bukkit.getScheduler().scheduleSyncDelayedTask(uhcRun, () -> victim.spigot().respawn());
    }


}
