package fr.timeuh;

import fr.timeuh.commands.Commands;
import fr.timeuh.game.State;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UHCRun extends JavaPlugin {
    private State state;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        setState(State.WAITING);

        getCommand("start").setExecutor(new Commands(this));
        getCommand("gstop").setExecutor(new Commands(this));
        //pluginManager.registerEvents(, this);
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
}
