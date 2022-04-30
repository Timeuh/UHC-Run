package fr.timeuh;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.timeuh.changes.FastThings;
import fr.timeuh.changes.ForbiddenThings;
import fr.timeuh.changes.TreeChop;
import fr.timeuh.commands.Commands;
import fr.timeuh.damages.HubDamages;
import fr.timeuh.damages.IngameDamages;
import fr.timeuh.game.State;
import fr.timeuh.game.WinConditions;
import fr.timeuh.inventories.ScenarioSelect;
import fr.timeuh.inventories.TeamSelect;
import fr.timeuh.players.Connections;
import fr.timeuh.players.GamePlayers;
import fr.timeuh.scenario.*;
import fr.timeuh.scoreboard.PlayerBoard;
import fr.timeuh.teams.TeamChat;
import fr.timeuh.teams.TeamList;
import fr.timeuh.worldgen.ModifyGeneration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public final class UHCRun extends JavaPlugin {
    private State state;
    private GamePlayers players;
    private WinConditions wins;
    private ScenarList scenario;
    private PlayerBoard board;
    private TeamList teams;
    private TreeChop chop;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        players = new GamePlayers(this);
        wins = new WinConditions(this);
        scenario = new ScenarList();
        board = new PlayerBoard(this);
        teams = new TeamList(this);
        chop = new TreeChop(this);

        setState(State.WAITING);
        scenario.enableScenario(Scenario.NOTEAMS);

        getCommand("start").setExecutor(new Commands(this));
        getCommand("gstop").setExecutor(new Commands(this));
        getCommand("setState").setExecutor(new Commands(this));
        getCommand("scenario").setExecutor(new Commands(this));
        getCommand("help").setExecutor(new Commands(this));
        getCommand("commandHelp").setExecutor(new Commands(this));
        getCommand("scenarioHelp").setExecutor(new Commands(this));

        pluginManager.registerEvents(new Connections(this), this);
        pluginManager.registerEvents(new HubDamages(this), this);
        pluginManager.registerEvents(new IngameDamages(this), this);
        pluginManager.registerEvents(new ScenarioSelect(this), this);
        pluginManager.registerEvents(new TeamSelect(this), this);
        pluginManager.registerEvents(new HasteyBoys(this), this);
        pluginManager.registerEvents(new Cutclean(this), this);
        pluginManager.registerEvents(new Timber(this), this);
        pluginManager.registerEvents(new FastThings(this), this);
        pluginManager.registerEvents(new ForbiddenThings(this), this);
        pluginManager.registerEvents(new ModifyGeneration(this), this);
        pluginManager.registerEvents(new TeamChat(this), this);
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

    public GamePlayers getPlayers() {
        return players;
    }

    public WinConditions getWins() {
        return wins;
    }

    public ScenarList getScenario() {
        return scenario;
    }

    public PlayerBoard getBoard() {
        return board;
    }

    public TeamList getTeams() {
        return teams;
    }

    public TreeChop getChop() {
        return chop;
    }
}
