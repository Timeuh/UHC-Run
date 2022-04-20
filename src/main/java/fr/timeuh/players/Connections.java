package fr.timeuh.players;

import fr.timeuh.UHCRun;
import fr.timeuh.game.State;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
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
            player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Bienvenue dans cette partie, si tu es perdu, pense au" + ChatColor.DARK_RED + "/help");
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
            player.setHealth(20);
            player.setLevel(0);
            player.setExp(0);
            player.setStatistic(Statistic.PLAYER_KILLS, 0);
            uhcRun.getBoard().joinScoreboard(player);
            uhcRun.getBoard().updateLobby();
            event.setJoinMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " rejoint les runners");
        }
    }

    @EventHandler
    public void onReconnection(PlayerJoinEvent event){
        if (uhcRun.isState(State.STARTING) || uhcRun.isState(State.PLAYING) || uhcRun.isState(State.PVP)){
            Player player = event.getPlayer();
            if (uhcRun.getPlayers().containsLivePlayer(player.getUniqueId())){
                uhcRun.getBoard().joinScoreboard(player);
                event.setJoinMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " est revenu !");
            } else {
                for (PotionEffect effect : player.getActivePotionEffects()) player.removePotionEffect(effect.getType());
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "La partie est déjà démarrée");
                uhcRun.getPlayers().addPlayer(player.getUniqueId());
                event.setJoinMessage(null);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (uhcRun.isState(State.WAITING) || uhcRun.isState(State.FINISH)){
            uhcRun.getPlayers().removePlayer(player.getUniqueId());
            if (uhcRun.getPlayers().containsLivePlayer(player.getUniqueId())) uhcRun.getPlayers().removeLivePlayer(player.getUniqueId());
            event.setQuitMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " quitte les runners");
            uhcRun.getBoard().updateLobby();
        } else {
            event.setQuitMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Le joueur " + ChatColor.DARK_RED + player.getName() + ChatColor.GOLD + " va bientôt revenir !");
        }
    }
}
