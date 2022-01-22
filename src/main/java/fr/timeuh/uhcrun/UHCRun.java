package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.CommandTest;
import fr.timeuh.uhcrun.listeners.UHCRunListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class UHCRun extends JavaPlugin {

    private GameState state;
    private List<Player> players = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();

        getCommand("test").setExecutor(new CommandTest(this));
        getCommand("gamebroadcast").setExecutor(new CommandTest());
        getCommand("spawn").setExecutor(new CommandTest());
        getCommand("gamestop").setExecutor(new CommandTest(this));
        getCommand("gamestart").setExecutor(new CommandTest(this));
        pluginManager.registerEvents(new UHCRunListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setState(GameState state){
        this.state = state;
    }

    public boolean isState(GameState state){
        return this.state == state;
    }

    public List<Player> getPlayers() {
        return players;
    }
}