package com.timeuh.dsuhc;

import com.timeuh.dsuhc.commands.CommandTest;
import com.timeuh.dsuhc.listeners.DSUHCListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public final class DemonSlayerUHC extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("test").setExecutor(new CommandTest(this));
        getCommand("gamebroadcast").setExecutor(new CommandTest());
        getCommand("spawn").setExecutor(new CommandTest());
        getServer().getPluginManager().registerEvents(new DSUHCListener(), this);

        Bukkit.getScheduler().runTaskTimer(this, new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("test");
            }
        }, 0,20*10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
