package fr.timeuh;

import fr.timeuh.commands.Commands;
import fr.timeuh.game.State;
import fr.timeuh.players.Connections;
import fr.timeuh.players.GamePlayers;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UHCRun extends JavaPlugin {
    private State state;
    private GamePlayers players;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        setState(State.WAITING);
        players = new GamePlayers();

        getCommand("start").setExecutor(new Commands(this));
        getCommand("gstop").setExecutor(new Commands(this));
        getCommand("setState").setExecutor(new Commands(this));

        pluginManager.registerEvents(new Connections(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setState(State newState){
        state = newState;
    }

    public boolean isState(State verifyState){
        return state == verifyState;
    }

    public GamePlayers getPlayers() {
        return players;
    }
}
