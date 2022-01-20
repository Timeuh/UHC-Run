package com.timeuh.dsuhc;

import com.timeuh.dsuhc.commands.CommandTest;
import com.timeuh.dsuhc.listeners.DSUHCListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DemonSlayerUHC extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("test").setExecutor(new CommandTest(this));
        getCommand("gamebroadcast").setExecutor(new CommandTest());
        getCommand("spawn").setExecutor(new CommandTest());
        getServer().getPluginManager().registerEvents(new DSUHCListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}