package fr.timeuh.players;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayers {
    private List<UUID> coPlayers;
    private List<UUID> alivePlayers;

    public GamePlayers() {
        this.coPlayers = new ArrayList<>();
        this.alivePlayers = new ArrayList<>();
    }

    public void addPlayer(UUID newPlayer){
        coPlayers.add(newPlayer);
    }

    public void addLivePlayer(UUID newPlayer){
        alivePlayers.add(newPlayer);
    }

    public List<UUID> getCoPlayers() {
        return coPlayers;
    }

    public List<UUID> getAlivePlayers() {
        return alivePlayers;
    }

    public void removePlayer(UUID delPlayer){
        coPlayers.remove(delPlayer);
    }

    public void removeLivePlayer(UUID delPlayer){
        alivePlayers.remove(delPlayer);
    }

}
