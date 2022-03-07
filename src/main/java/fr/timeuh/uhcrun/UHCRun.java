package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.GameCommands;
import fr.timeuh.uhcrun.listeners.GameDamageListener;
import fr.timeuh.uhcrun.listeners.GameListener;
import fr.timeuh.uhcrun.tasks.GameCycle;
import fr.timeuh.uhcrun.tasks.GameStop;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public final class UHCRun extends JavaPlugin {

    private GameState state;
    private List<Player> players = new ArrayList<>();
    private List<Player> alivePlayers = new ArrayList<>();
    private List<Player> cancelDamagePlayer = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();
    private List<Location> pvp = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();
        PlayerTeams teams = new PlayerTeams();

        buildSpawns();
        buildPVP();
        getCommand("test").setExecutor(new GameCommands(this, teams));
        getCommand("broadcast").setExecutor(new GameCommands());
        getCommand("spawn").setExecutor(new GameCommands());
        getCommand("gamestop").setExecutor(new GameCommands(this, teams));
        getCommand("start").setExecutor(new GameCommands(this, teams));

        pluginManager.registerEvents(new GameListener(this, teams), this);
        pluginManager.registerEvents(new GameDamageListener(this, teams), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void buildSpawns(){
        World world = Bukkit.getWorld("world");
        spawns.add(new Location(world,1300,100,1300,12f,17f));
        spawns.add(new Location(world,1300,100,-1300,12f,17f));
        spawns.add(new Location(world,-1300,100,1300,12f,17f));
        spawns.add(new Location(world,-1300,100,-1300,12f,17f));

        spawns.add(new Location(world,-1100,100,0,12f,17f));
        spawns.add(new Location(world,1100,100,0,12f,17f));
        spawns.add(new Location(world,0,100,-1100,12f,17f));
        spawns.add(new Location(world,0,100,1100,12f,17f));

        spawns.add(new Location(world,1010,100,1000,12f,17f));
        spawns.add(new Location(world,1000,100,-1000,12f,17f));
        spawns.add(new Location(world,-1000,100,1000,12f,17f));
        spawns.add(new Location(world,-1000,100,-1000,12f,17f));

        spawns.add(new Location(world,800,100,0,12f,17f));
        spawns.add(new Location(world,-800,100,0,12f,17f));
        spawns.add(new Location(world,0,100,800,12f,17f));
        spawns.add(new Location(world,0,100,-800,12f,17f));

        spawns.add(new Location(world,500,100,500,12f,17f));
        spawns.add(new Location(world,500,100,-500,12f,17f));
        spawns.add(new Location(world,-500,100,500,12f,17f));
        spawns.add(new Location(world,-500,100,-500,12f,17f));
    }

    public void buildPVP(){
        World world = Bukkit.getWorld("world");
        pvp.add(new Location(world,1000,100,1000,12f,17f));
        pvp.add(new Location(world,1000,100,-1000,12f,17f));
        pvp.add(new Location(world,-1000,100,1000,12f,17f));
        pvp.add(new Location(world,-1000,100,-1000,12f,17f));

        pvp.add(new Location(world,-800,100,0,12f,17f));
        pvp.add(new Location(world,800,100,0,12f,17f));
        pvp.add(new Location(world,0,100,-800,12f,17f));
        pvp.add(new Location(world,0,100,800,12f,17f));

        pvp.add(new Location(world,600,100,600,12f,17f));
        pvp.add(new Location(world,600,100,-600,12f,17f));
        pvp.add(new Location(world,-600,100,600,12f,17f));
        pvp.add(new Location(world,-600,100,-600,12f,17f));

        pvp.add(new Location(world,400,100,0,12f,17f));
        pvp.add(new Location(world,-400,100,0,12f,17f));
        pvp.add(new Location(world,0,100,400,12f,17f));
        pvp.add(new Location(world,0,100,-400,12f,17f));

        pvp.add(new Location(world,200,100,200,12f,17f));
        pvp.add(new Location(world,200,100,-200,12f,17f));
        pvp.add(new Location(world,-200,100,200,12f,17f));
        pvp.add(new Location(world,-200,100,-200,12f,17f));
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

    public List<Player> getCancelDamagePlayers() {
        return cancelDamagePlayer;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<Location> getPvp() {
        return pvp;
    }

    public void addCancelDamage(Player player){
        cancelDamagePlayer.add(player);
    }

    public void removeCancelDamage(Player player) {
        cancelDamagePlayer.remove(player);
    }

    public void eliminate(Player player, PlayerTeams teams) {
       if (alivePlayers.contains(player)) {
           alivePlayers.remove(player);
           player.setGameMode(GameMode.SPECTATOR);
           player.sendMessage("§5[UHCRun] §6Vous êtes mort, cheh !");
           checkWin(this, teams);
       }
    }

    public void checkWin(UHCRun uhcRun, PlayerTeams teams){
        if (alivePlayers.size() == 1){
            Player winner = alivePlayers.get(0);
            Bukkit.broadcastMessage("§5[UHCRun] §4"+winner.getName() + " §6Gagne cette game !");
            GameStop stop = new GameStop(this, teams);
            stop.runTaskTimer(uhcRun, 0, 20);
        }

        if (alivePlayers.size() == 0){
            Bukkit.broadcastMessage("§5[UHCRun] §6Tout le monde est mort ! Il n'y a pas de gagnant");
            GameStop stop = new GameStop(this, teams);
            stop.runTaskTimer(uhcRun, 0, 20);
        }
    }

    public void createBoard(Player player, PlayerTeams teams){
        Objective obj = teams.board.getObjective("UHCRun");
        obj.unregister();
        obj = teams.board.registerNewObjective("UHCRun", "dummy");
        obj.setDisplayName("§5UHCRun §6by §4Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("§6-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore("§6Joueurs en vie : §4"+alivePlayers.size());
        score1.setScore(2);
        Score score2 = obj.getScore("§6Phase PvP dans §4"+ GameCycle.getTimer() +"§6 secondes");
        score2.setScore(1);
    }

    public void createPVPBoard(Player player, PlayerTeams teams) {
        Objective obj = teams.board.getObjective("UHCRunPVP");
        obj.unregister();
        obj = teams.board.registerNewObjective("UHCRunPVP", "dummy");
        obj.setDisplayName("§5UHCRun §6by §4Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("§6-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore("§6Joueurs en vie : §4"+alivePlayers.size());
        score1.setScore(2);
        Score score3 = obj.getScore("§6Kills : §4"+ player.getStatistic(Statistic.PLAYER_KILLS));
        score3.setScore(1);
        Score score4 = obj.getScore("§6Phase PvP §4débutée");
        score4.setScore(0);
    }

    public void createLobbyBoard(PlayerTeams teams){
        Objective obj = teams.board.getObjective("UHCRunLobby");
        obj.unregister();
        obj = teams.board.registerNewObjective("UHCRunLobby", "dummy");
        obj.setDisplayName("§5UHCRun §6by §4Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("§6-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore("§6Bienvenue dans cet UHC Run !");
        score1.setScore(2);
        Score score2 = obj.getScore("§6PVP : §4"+20 +"§6 minutes");
        score2.setScore(1);
        Score score3 = obj.getScore("§6Joueurs en ligne : §4" + getPlayers().size() + "§6/§4" + Bukkit.getMaxPlayers());
        score3.setScore(0);
    }

    public void beInsensible(Player player){
        addCancelDamage(player);
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            int timer = 0;
            @Override
            public void run() {
                if (timer == 30){
                    removeCancelDamage(player);
                    player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Votre periode d'invincibilite est terminee");
                }
                timer ++;
            }
        }, 0, 20);
    }
}