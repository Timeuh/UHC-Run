package fr.timeuh.players;

import fr.timeuh.UHCRun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class GamePlayers {
    private List<UUID> coPlayers;
    private List<UUID> alivePlayers;
    private List<UUID> noDamagePlayers;
    private List<UUID> decoPlayers;
    private HashMap<UUID, Team> decoPlayerTeam;
    private UHCRun uhcRun;

    public GamePlayers(UHCRun uhcRun) {
        this.coPlayers = new ArrayList<>();
        this.alivePlayers = new ArrayList<>();
        this.noDamagePlayers = new ArrayList<>();
        this.decoPlayers = new ArrayList<>();
        this.decoPlayerTeam = new HashMap<>();
        this.uhcRun = uhcRun;
    }

    public void addPlayer(UUID newPlayer){
        coPlayers.add(newPlayer);
    }

    public void addLivePlayer(UUID newPlayer){
        alivePlayers.add(newPlayer);
    }

    public void addInvinciblePlayer(UUID newPlayer){
        noDamagePlayers.add(newPlayer);
    }

    public void addDecoPlayer(UUID newPlayer){
        decoPlayers.add(newPlayer);
    }

    public void addDecoPlayerTeam(UUID newPlayer, Team playerTeam){
        decoPlayerTeam.put(newPlayer, playerTeam);
    }

    public List<UUID> getCoPlayers() {
        return coPlayers;
    }

    public List<UUID> getAlivePlayers() {
        return alivePlayers;
    }

    public List<UUID> getInvinciblePlayers() {
        return noDamagePlayers;
    }

    public List<UUID> getDecoPlayers() {
        return decoPlayers;
    }

    public HashMap<UUID, Team> getDecoPlayerTeam() {
        return decoPlayerTeam;
    }

    public void removePlayer(UUID delPlayer){
        coPlayers.remove(delPlayer);
    }

    public void removeLivePlayer(UUID delPlayer){
        alivePlayers.remove(delPlayer);
    }

    public void removeInvinciblePlayer(UUID delPlayer){
        noDamagePlayers.remove(delPlayer);
    }

    public void removeDecoPlayer(UUID delPlayer){
        decoPlayers.remove(delPlayer);
    }

    public void removeDecoPlayerTeam(UUID delPlayer){
        decoPlayerTeam.remove(delPlayer);
    }

    public boolean containsPlayer(UUID presentPlayer){
        return coPlayers.contains(presentPlayer);
    }

    public boolean containsLivePlayer(UUID presentPlayer){
        return alivePlayers.contains(presentPlayer);
    }

    public boolean containsInvinciblePlayer(UUID presentPlayer){
        return noDamagePlayers.contains(presentPlayer);
    }

    public boolean containsDecoPlayer(UUID presentPlayer){
        return decoPlayers.contains(presentPlayer);
    }

    public void beInvincible(Player player){
        addInvinciblePlayer(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Vous avez" + ChatColor.DARK_RED + " 30 secondes" + ChatColor.GOLD  + " d'insensibilité");
        Bukkit.getScheduler().runTaskTimer(uhcRun, new Runnable() {
            int timer = 0;
            @Override
            public void run() {
                if (timer == 30){
                    removeInvinciblePlayer(player.getUniqueId());
                    player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Vous n'êtes plus insensible");
                }
                timer ++;
            }
        }, 0, 20);
    }

    public List<Player> allCoPlayers(){
        List<Player> connectedPlayers = new ArrayList<>();
        for (UUID playerUUID : coPlayers){
            connectedPlayers.add(Bukkit.getPlayer(playerUUID));
        }
        return connectedPlayers;
    }

    public List<Player> allLivePlayers(){
        List<Player> livePlayers = new ArrayList<>();
        for (UUID playerUUID : alivePlayers){
            livePlayers.add(Bukkit.getPlayer(playerUUID));
        }
        return livePlayers;
    }

    public List<Player> allInvinciblePlayers(){
        List<Player> insensiblePlayers = new ArrayList<>();
        for (UUID playerUUID : noDamagePlayers){
            insensiblePlayers.add(Bukkit.getPlayer(playerUUID));
        }
        return insensiblePlayers;
    }

    public List<Player> allDecoPlayers(){
        List<Player> deconnectedPlayers = new ArrayList<>();
        for (UUID playerUUID : decoPlayers){
            deconnectedPlayers.add(Bukkit.getPlayer(playerUUID));
        }
        return deconnectedPlayers;
    }

    public void teleportPlayers(){
        Random coordinates = new Random();

        for (Player player : allLivePlayers()) {
            if (player != null) {
                int x = coordinates.nextInt(1500);
                int z = coordinates.nextInt(1500);
                int firstMinus = coordinates.nextInt(2);
                int secondMinus = coordinates.nextInt(2);

                if (firstMinus > 0) x = -x;
                if (secondMinus > 0) z = -z;

                Location teleport = new Location(Bukkit.getWorld("world"), x, 100, z);
                player.teleport(teleport);
            }
        }
    }

}
