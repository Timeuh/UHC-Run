package fr.timeuh;

import fr.timeuh.commands.Commands;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UHCRun extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        getCommand("start").setExecutor(new Commands(this));
        getCommand("gstop").setExecutor(new Commands(this));
        //pluginManager.registerEvents(, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
