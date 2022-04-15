package fr.timeuh.players;

import fr.timeuh.UHCRun;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Connections implements Listener {
    private UHCRun uhcRun;

    public Connections(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event){
        if (uhcRun.isState(S))
    }

    @EventHandler
    public void onReconnection(PlayerJoinEvent event){

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

    }
}
