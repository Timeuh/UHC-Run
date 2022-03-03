package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.GameCommands;
import fr.timeuh.uhcrun.listeners.GameDamageListener;
import fr.timeuh.uhcrun.listeners.GameListener;
import fr.timeuh.uhcrun.tasks.GameCycle;
import fr.timeuh.uhcrun.tasks.GameStop;
import fr.timeuh.uhcrun.teamlists.TabList;
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
    private List<Player> cancelFallPlayer = new ArrayList<>();
    private List<Location> spawns = new ArrayList<>();
    private List<Location> pvp = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();

        buildSpawns();
        buildPVP();
        getCommand("test").setExecutor(new GameCommands(this));
        getCommand("broadcast").setExecutor(new GameCommands());
        getCommand("spawn").setExecutor(new GameCommands());
        getCommand("gamestop").setExecutor(new GameCommands(this));
        getCommand("start").setExecutor(new GameCommands(this));

        pluginManager.registerEvents(new GameListener(this), this);
        pluginManager.registerEvents(new GameDamageListener(this), this);
        TabList.onTab(this);
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

        spawns.add(new Location(world,1000,100,1000,12f,17f));
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

    public List<Player> getNoFallPlayers() {
        return cancelFallPlayer;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<Location> getPvp() {
        return pvp;
    }

    public void ajouterNoFall(Player player){
        cancelFallPlayer.add(player);
    }

    public void supprimerNoFall(Player player) {
        cancelFallPlayer.remove(player);
    }

    public void eliminate(Player player) {
       if (alivePlayers.contains(player)) {
           alivePlayers.remove(player);
           player.setGameMode(GameMode.SPECTATOR);
           player.sendMessage("§5[UHCRun] §6Vous êtes mort, cheh !");
           checkWin(this);
       }
    }

    public void checkWin(UHCRun uhcRun){
        if (alivePlayers.size() == 1){
            Player winner = alivePlayers.get(0);
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

    public void createBoard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("UHCRun","dummy");
        obj.setDisplayName("§5UHCRun §6by §4Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore("§6-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore("§6Joueurs en vie : §4"+alivePlayers.size());
        score1.setScore(2);
        Score score2 = obj.getScore("§6Phase PvP dans §4"+ GameCycle.getTimer() +"§6 secondes");
        score2.setScore(1);
        player.setScoreboard(board);
    }

    public void createPVPBoard(Player player) {
        ScoreboardManager manager2 = Bukkit.getScoreboardManager();
        Scoreboard boardPVP = manager2.getNewScoreboard();
        Objective obj = boardPVP.registerNewObjective("UHCRunPVP","dummy");
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
        player.setScoreboard(boardPVP);
    }

    public void createLobbyBoard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("UHCRunLobby","dummy");
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
        player.setScoreboard(board);
    }
}