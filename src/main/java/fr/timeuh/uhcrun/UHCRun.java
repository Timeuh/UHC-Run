package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.GameCommands;
import fr.timeuh.uhcrun.listeners.GameDamageListener;
import fr.timeuh.uhcrun.listeners.GameListener;
import fr.timeuh.uhcrun.tasks.GameStop;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class UHCRun extends JavaPlugin {

    private GameState state;
    private List<Player> players = new ArrayList<>();
    private List<Player> alivePlayers = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();
        World world = Bukkit.getWorld("world");

        spawns.add(new Location(world,25,100,25,12f,17f));
        spawns.add(new Location(world,-25,100,-25,12f,17f));

        getCommand("test").setExecutor(new GameCommands(this));
        getCommand("broadcast").setExecutor(new GameCommands());
        getCommand("spawn").setExecutor(new GameCommands());
        getCommand("stop").setExecutor(new GameCommands(this));
        getCommand("start").setExecutor(new GameCommands(this));

        pluginManager.registerEvents(new GameListener(this), this);
        pluginManager.registerEvents(new GameDamageListener(this), this);

        WorldBorder border = world.getWorldBorder();
        border.setCenter(0,0);
        border.setSize(500);
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

    public List<Player> getAlivePlayers() {
        return alivePlayers;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void eliminate(Player player) {
       if (alivePlayers.contains(player)) {
           alivePlayers.remove(player);
           player.setGameMode(GameMode.SPECTATOR);
           player.sendMessage("Vous êtes mort, cheh !");
           checkWin(this);
       }
    }

    public void checkWin(UHCRun uhcRun){
        if (alivePlayers.size() == 1){
            Player winner = alivePlayers.get(0);
            Location spawn = new Location(winner.getWorld(), 0, 65, 0);
            uhcRun.setState(GameState.FINISH);
            winner.setLevel(0);
            winner.sendMessage("§6Arrêt de la partie ...");
            winner.sendMessage("§6Téléportation au spawn...");
            winner.getInventory().clear();
            winner.teleport(spawn);
            Bukkit.broadcastMessage("§5[UHCRun] §4"+winner.getName() + " §6Gagne cette game !");
            GameStop stop = new GameStop(this);
            stop.runTaskTimer(uhcRun, 0, 20);
        }

        if (alivePlayers.size() == 0){
            Bukkit.broadcastMessage("§5[UHCRun] §6Tout le monde est mort ! Il n'y a pas de gagnant");
            GameStop stop = new GameStop(this);
            stop.runTaskTimer(uhcRun, 0, 20);
        }
    }

}