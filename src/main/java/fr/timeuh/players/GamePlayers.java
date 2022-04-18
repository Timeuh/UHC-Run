package fr.timeuh.players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayers {
    private List<UUID> coPlayers;
    private List<UUID> alivePlayers;
    private List<UUID> noDamagePlayers;

    public GamePlayers() {
        this.coPlayers = new ArrayList<>();
        this.alivePlayers = new ArrayList<>();
        this.noDamagePlayers = new ArrayList<>();
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

    public List<UUID> getCoPlayers() {
        return coPlayers;
    }

    public List<UUID> getAlivePlayers() {
        return alivePlayers;
    }

    public List<UUID> getInvinciblePlayers() {
        return noDamagePlayers;
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

    public boolean containsPlayer(UUID presentPlayer){
        return coPlayers.contains(presentPlayer);
    }

    public boolean containsLivePlayer(UUID presentPlayer){
        return alivePlayers.contains(presentPlayer);
    }

    public boolean containsInvinciblePlayer(UUID presentPlayer){
        return noDamagePlayers.contains(presentPlayer);
    }

}
