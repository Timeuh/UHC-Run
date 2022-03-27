package fr.timeuh.uhcrun;

import fr.timeuh.uhcrun.commands.GameCommands;
import fr.timeuh.uhcrun.listeners.GameDamageListener;
import fr.timeuh.uhcrun.listeners.GameListener;
import fr.timeuh.uhcrun.listeners.ModifiedDropsListener;
import fr.timeuh.uhcrun.listeners.WorldGenListener;
import fr.timeuh.uhcrun.scenarios.Scenarios;
import fr.timeuh.uhcrun.scenarios.Timber;
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
    private List<Scenarios> enabledScenarios = new ArrayList<>();
    private List<Scenarios> allScenarios = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        //setState(GameState.WAITING);
        PluginManager pluginManager = getServer().getPluginManager();

        buildSpawns();
        buildPVP();
        createScenarios();
        enableScenarios(Scenarios.NOTEAMS);
        enableScenarios(Scenarios.TIMBER);

        getCommand("broadcast").setExecutor(new GameCommands());
        getCommand("spawn").setExecutor(new GameCommands());
        getCommand("help").setExecutor(new GameCommands());
        getCommand("seeEnabledScenarios").setExecutor(new GameCommands(this));
        getCommand("gamestop").setExecutor(new GameCommands(this));
        getCommand("start").setExecutor(new GameCommands(this));

        pluginManager.registerEvents(new WorldGenListener(this), this);
        pluginManager.registerEvents(new GameListener(this), this);
        pluginManager.registerEvents(new GameDamageListener(this), this);
        pluginManager.registerEvents(new ModifiedDropsListener(this), this);
        pluginManager.registerEvents(new Timber(this), this);
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

    public List<Scenarios> getEnabledScenarios() {
        return enabledScenarios;
    }

    public void addCancelDamage(Player player){
        cancelDamagePlayer.add(player);
    }

    public void removeCancelDamage(Player player) {
        cancelDamagePlayer.remove(player);
    }

    public void eliminatePlayer(Player player) {
       if (alivePlayers.contains(player)) {
           alivePlayers.remove(player);
           player.setGameMode(GameMode.SPECTATOR);
           player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Vous êtes mort, cheh !");
           if (checkEnabledScenario(Scenarios.TEAMS)) {
               Team playerTeam = player.getScoreboard().getEntryTeam(player.getName());
               PlayerTeams.leaveTeamIngame(player, this);
               PlayerTeams.updateTeams(this);
               if (PlayerTeams.isTeamEliminated(player, playerTeam)) {
                   ChatColor color = PlayerTeams.getTeamColor(playerTeam);
                   Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "L'équipe " + color + ChatColor.BOLD+ playerTeam.getName() + ChatColor.GOLD + " est éliminée !");
               }
           }
           checkWin(this, player);
       }
    }

    public void checkWin(UHCRun uhcRun, Player player){
        if (checkEnabledScenario(Scenarios.NOTEAMS)) {
            if (alivePlayers.size() == 1) {
                Player winner = alivePlayers.get(0);
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + ChatColor.BOLD + winner.getName() + ChatColor.GOLD + " gagne cette partie !");
                GameStop stop = new GameStop(this);
                stop.runTaskTimer(uhcRun, 0, 20);
            } else if (alivePlayers.size() == 0) {
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Tout le monde est mort ! Il n'y a pas de gagnant");
                GameStop stop = new GameStop(this);
                stop.runTaskTimer(uhcRun, 0, 20);
            }
        } else if (checkEnabledScenario(Scenarios.TEAMS)){
            if (PlayerTeams.oneTeamRemaining(player)){
                String winnerName = PlayerTeams.getLastTeam(player).getName();
                ChatColor color = PlayerTeams.getTeamColor(PlayerTeams.getLastTeam(player));
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "L'équipe " + color + ChatColor.BOLD + winnerName + ChatColor.GOLD + " gagne cette game !");
                GameStop stop = new GameStop(this);
                stop.runTaskTimer(uhcRun, 0, 20);
            }
        }
    }

    public boolean everyPlayerInTeam(){
        if (checkEnabledScenario(Scenarios.TEAMS)){
            int counter = 0;
            for (Player player : getPlayers()){
                if (PlayerTeams.hasTeam(player)){
                    counter++;
                }
            }
            return (counter == getPlayers().size());
        } else {
            return true;
        }
    }

    public void createBoard(Player player){
        Objective obj = player.getScoreboard().getObjective("UHCRun");
        obj.unregister();
        obj = player.getScoreboard().registerNewObjective("UHCRun", "dummy");
        obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun " + ChatColor.GOLD + "by " + ChatColor.DARK_RED + "Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore(ChatColor.GOLD + "Joueurs en vie : " + ChatColor.DARK_RED +alivePlayers.size());
        score1.setScore(2);
        Score score2 = obj.getScore(ChatColor.GOLD + "Phase PvP dans "+ ChatColor.DARK_RED + GameCycle.getTimer() + ChatColor.GOLD + " secondes");
        score2.setScore(1);
    }

    public void createPVPBoard(Player player) {
        Objective obj = player.getScoreboard().getObjective("UHCRunPVP");
        obj.unregister();
        obj = player.getScoreboard().registerNewObjective("UHCRunPVP", "dummy");
        obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun " + ChatColor.GOLD + "by " + ChatColor.DARK_RED + "Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore(ChatColor.GOLD + "Joueurs en vie : " + ChatColor.DARK_RED +alivePlayers.size());
        score1.setScore(2);
        Score score3 = obj.getScore(ChatColor.GOLD + "Kills : "+ ChatColor.DARK_RED + (player.getStatistic(Statistic.PLAYER_KILLS)));
        score3.setScore(1);
        Score score4 = obj.getScore(ChatColor.GOLD + "Phase PvP " + ChatColor.DARK_RED + "débutée");
        score4.setScore(0);
    }

    public void createLobbyBoard(Player player){
        Objective obj = player.getScoreboard().getObjective("UHCRunLobby");
        obj.unregister();
        obj = player.getScoreboard().registerNewObjective("UHCRunLobby", "dummy");
        obj.setDisplayName(ChatColor.DARK_PURPLE + "UHCRun " + ChatColor.GOLD + "by " + ChatColor.DARK_RED + "Timeuh");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = obj.getScore(ChatColor.GOLD + "-------------------------");
        score.setScore(3);
        Score score1 = obj.getScore(ChatColor.GOLD +"Bienvenue dans cet UHC Run !");
        score1.setScore(2);
        Score score2 = obj.getScore(ChatColor.GOLD + "PVP : " + ChatColor.DARK_RED + 20 + ChatColor.GOLD + " minutes");
        score2.setScore(1);
        Score score3 = obj.getScore(ChatColor.GOLD + "Joueurs en ligne : " + ChatColor.DARK_RED + getPlayers().size() + ChatColor.GOLD + "/" + ChatColor.GOLD + Bukkit.getMaxPlayers());
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
                    player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Votre période d'invincibilite est terminée");
                }
                timer ++;
            }
        }, 0, 20);
    }

    public boolean checkEnabledScenario(Scenarios toCheck){
        for (Scenarios scenario : enabledScenarios){
            if (scenario.equals(toCheck)){
                return true;
            }
        }
        return false;
    }

    public void enableScenarios(Scenarios scenarioName){
        boolean presence = false;
        for (Scenarios scenario : enabledScenarios){
            if (scenario.equals(scenarioName)){
                presence = true;
            }
        }
        if (!presence){
            enabledScenarios.add(scenarioName);
        }
    }

    public void disableScenario(Scenarios scenarioName){
        boolean presence = false;
        for (Scenarios scenario : enabledScenarios){
            if (scenario.equals(scenarioName)){
                presence = true;
            }
        }
        if (presence){
            enabledScenarios.remove(scenarioName);
        }
    }

    public String getScenarioName(Scenarios scenario){
        switch (scenario){
            case TEAMS:
                return "Teams";
            case NOTEAMS:
                return "NoTeams";
            case FRIENDLYFIRE:
                return "FriendlyFire";
            case TIMBER:
                return "Timber";
            case CUTCLEAN:
                return "CutClean";
            case HASTEYBOYS:
                return "HasteyBoys";
            default:
                return null;
        }
    }

    public void createScenarios(){
        allScenarios.add(Scenarios.CUTCLEAN);
        allScenarios.add(Scenarios.FRIENDLYFIRE);
        allScenarios.add(Scenarios.HASTEYBOYS);
        allScenarios.add(Scenarios.NOTEAMS);
        allScenarios.add(Scenarios.TEAMS);
        allScenarios.add(Scenarios.TIMBER);
    }

    public List<Scenarios> getAllScenarios(){
        return allScenarios;
    }

}