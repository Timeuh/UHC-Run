package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.CommandTest;
import fr.timeuh.uhcrun.listeners.UHCRunDamageListener;
import fr.timeuh.uhcrun.listeners.UHCRunListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class UHCRun extends JavaPlugin {

    private GameState state;
    private List<Player> players = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();
        World world = Bukkit.getWorld("world");

        spawns.add(new Location(world,25,65,25,12f,17f));
        spawns.add(new Location(world,-25,65,-25,12f,17f));

        getCommand("test").setExecutor(new CommandTest(this));
        getCommand("gamebroadcast").setExecutor(new CommandTest());
        getCommand("spawn").setExecutor(new CommandTest());
        getCommand("gamestop").setExecutor(new CommandTest(this));
        getCommand("gamestart").setExecutor(new CommandTest(this));

        pluginManager.registerEvents(new UHCRunListener(this), this);
        pluginManager.registerEvents(new UHCRunDamageListener(this), this);
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

    public List<Location> getSpawns() {
        return spawns;
    }

    public void eliminate(Player player) {
       if (players.contains(player)) {
           players.remove(player);
           player.setGameMode(GameMode.SPECTATOR);
           player.sendMessage("Vous êtes mort, cheh !");
           checkWin();
       }
    }

    public void checkWin(){
        if (players.size() == 1){
            Player winner = players.get(0);
            Bukkit.broadcastMessage("§5[UHCRun] "+winner.getName() + " Gagne cette game !");
        }
    }

}