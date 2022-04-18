package fr.timeuh.players;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Connections implements Listener {
    private UHCRun uhcRun;

    public Connections(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)){
            Player player = event.getPlayer();
            uhcRun.getPlayers().addPlayer(player.getUniqueId());
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
            player.setHealth(20);
            event.setJoinMessage(null);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " rejoint les runners");
        }
    }

    @EventHandler
    public void onReconnection(PlayerJoinEvent event){
        if (uhcRun.isState(State.STARTING) || uhcRun.isState(State.PLAYING) || uhcRun.isState(State.PVP)){
            Player player = event.getPlayer();
            event.setJoinMessage(null);
            if (uhcRun.getPlayers().containsLivePlayer(player.getUniqueId())){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Reconnexion du joueur " + ChatColor.DARK_RED + player.getName());
            } else {
                for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La partie est déjà démarrée");
                uhcRun.getPlayers().addPlayer(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)){
            Player player = event.getPlayer();
            uhcRun.getPlayers().removePlayer(player.getUniqueId());
            if (uhcRun.getPlayers().containsLivePlayer(player.getUniqueId())) uhcRun.getPlayers().removeLivePlayer(player.getUniqueId());
            event.setQuitMessage(null);
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " quitte les runners");
        }
    }
}
