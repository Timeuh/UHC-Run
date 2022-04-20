package fr.timeuh.commands;

import fr.timeuh.UHCRun;
import fr.timeuh.game.Start;
import fr.timeuh.game.State;
import fr.timeuh.game.Stop;
import fr.timeuh.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private UHCRun uhcRun;

    public Commands(UHCRun uhcRun) {
        this.uhcRun = uhcRun;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            switch(command.getName()){
                case "start":
                    start(args, player);
                    return true;

                case "gstop":
                    stop(args, player);
                    return true;

                case "setState":
                    if (args.length == 0){
                        helpSetState(player);
                        return false;
                    } else {
                        setState(args[0], args, player);
                        return true;
                    }

                case "scenario":
                    displayScenarios(player, args);
                    return true;

                case "help":
                    help(player, args);
                    return true;

                case "commandHelp":
                    commandHelp(player, args);
                    return true;

                case "scenarioHelp":
                    scenarioHelp(player, args);
                    return true;
            }
        }
        return false;
    }

    private void start(String[] args, Player player){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/start <Pas d'arguments>");
        } else {
            if (uhcRun.getTeams().allPlayersInTeam()) {
                Start start = new Start(uhcRun);
                start.runTaskTimer(uhcRun, 0, 20);
            } else {
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Tous les joueurs doivent avoir une team pour lancer la partie");
            }
        }
    }

    private void stop(String[] args, Player player){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/gstop <Pas d'arguments>");
        } else {
            Stop stop = new Stop(uhcRun);
            stop.runTaskTimer(uhcRun, 0, 20);
        }
    }

    private void setState(String state, String[] args, Player player){
        if (wrongLengthOneArgument(args) || state == null){
            helpSetState(player);
        } else {
            switch (state) {
                case "waiting":
                    uhcRun.setState(State.WAITING);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "WAITING");
                    break;
                case "starting":
                    uhcRun.setState(State.STARTING);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "STARTING");
                    break;
                case "playing":
                    uhcRun.setState(State.PLAYING);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "PLAYING");
                    break;
                case "pvp":
                    uhcRun.setState(State.PVP);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "PVP");
                    break;
                case "finish":
                    uhcRun.setState(State.FINISH);
                    Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "State " + ChatColor.DARK_RED + "FINISH");
                    break;

                default:
                    helpSetState(player);
            }
        }
    }

    private boolean wrongLengthNoArguments(String[] args){
        return args.length > 0;
    }

    private boolean wrongLengthOneArgument(String[] args){
        return args.length != 1;
    }

    private void helpSetState(Player player){
        player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[setState/ss] <waiting/starting/playing/pvp/finish>");
        player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "⚠ Cette commande est utilisée en développement pour les tests, elle n'est pas destinée à être utilisée pour les parties");
    }

    private void help(Player player, String[] args){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[help] <pas d'arguments>");
        } else {
            String separation = ChatColor.GOLD + "-----------------------------------------------------\n";
            String help = ChatColor.GOLD + "-----------------------" +ChatColor.DARK_RED + "[Help]" + ChatColor.GOLD + "------------------------\n";
            String bienvenue = ChatColor.GOLD + "Bienvenue dans cette partie d'" + ChatColor.DARK_RED + " UHCRun" + ChatColor.GOLD + "\nVoici la V2 de l'UHCRun (tu as peut-être même pas joué à la V1 d'ailleurs)" +
                    ".\nSi tu connais pas, le principe c'est un UHC (ultra hardcore) donc la seule régénération possible vient des potions ou des pommes en or, mais en accéléré. Au début de la partie et pendant" +
                    " 20 minutes, tu auras speed 2 et tu devras miner pour te faire de l'équipement.\nAu bout de ces 20 minutes, tous les joueurs seront téléportés à des points aléatoires et devront se battre, " +
                    "sachant que la bordure se réduit avec le temps qui passe et qu'elle peut tuer très rapidement les joueurs derrière.\nLe but est d'être le dernier survivant, ou la dernière équipe survivante " +
                    "si les équipes sont activées.";
            String scenarios = "Pour ce mode de jeu, il existe différents scénarios qui permettent de rendre plus rapide la partie. Si tu veux une description des scénarios, utilise" +
                    ChatColor.DARK_RED + " /scenarioHelp ou /sh" + ChatColor.GOLD + ".";
            String commandes = "Si t'es simple joueur, la moitié te sera pas utile, mais sache qu'il existe plusieurs commandes et que tu peux utiliser" + ChatColor.DARK_RED + " /commandHelp ou /ch"
                    + ChatColor.GOLD + " pour avoir une description détaillée de chaque.\n";
            player.sendMessage(help + bienvenue + separation + scenarios + separation + commandes + help);
        }
    }

    private void displayScenarios(Player player, String[] args){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[scenario] <pas d'arguments>");
        } else {
            String scenario = ChatColor.GOLD + "-----------------" +ChatColor.DARK_RED + "[Scenarios actifs]" + ChatColor.GOLD + "------------------\n";
            String scenariosActifs = "";
            for (Scenario scenar : uhcRun.getScenario().getAllScenarios()){
                if (uhcRun.getScenario().getActiveScenarios().contains(scenar)){
                    scenariosActifs += ChatColor.GOLD + "Scenario : " + ChatColor.DARK_RED + uhcRun.getScenario().getScenarioName(scenar) + ChatColor.GREEN + " ✔\n";
                } else {
                    scenariosActifs += ChatColor.GOLD + "Scenario : " + ChatColor.DARK_RED + uhcRun.getScenario().getScenarioName(scenar) + ChatColor.RED + " ✘\n";
                }
            }
            player.sendMessage(scenario + scenariosActifs + "\n" + scenario);
        }
    }

    private void commandHelp(Player player, String[] args){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[commandHelp/ch] <pas d'arguments>");
        } else {
            String command = ChatColor.GOLD + "---------------------" +ChatColor.DARK_RED + "[Commandes]" + ChatColor.GOLD + "---------------------\n";
            String listeCommandes = "Voici une liste des commandes avec leur descriptif :\n"
                    + "-" + ChatColor.DARK_RED + "/help" + ChatColor.GOLD + " : affiche l'aide du plugin\n"
                    + "-" + ChatColor.DARK_RED + "/start" + ChatColor.GOLD + " : lance un timer de 10 secondes pour commencer la partie\n"
                    + "-" + ChatColor.DARK_RED + "/gstop" + ChatColor.GOLD + " : lance un timer de 10 secondes pour terminer la partie\n"
                    + "-" + ChatColor.DARK_RED + "/setState" + ChatColor.GOLD + " : change le State du plugin, ⚠ c'est réservé au dévelopemment\n"
                    + "-" + ChatColor.DARK_RED + "/scenario" + ChatColor.GOLD + " : affiche la liste des scenarios, avec leur état d'activité (activé ou non)\n"
                    + "-" + ChatColor.DARK_RED + "/scenarioHelp" + ChatColor.GOLD + " : affiche l'aide liée aux scénarios\n"
                    + "-" + ChatColor.DARK_RED + "/commandHelp" + ChatColor.GOLD + " : affiche l'aide liée aux commandes, t'y es déjà\n";
            player.sendMessage(command + listeCommandes + "\n" + command);
        }
    }

    private void scenarioHelp(Player player, String[] args){
        if (wrongLengthNoArguments(args)){
            player.sendMessage(ChatColor.GOLD + "L'usage pour cette commande est : " + ChatColor.DARK_RED + "/[scenarioHelp/sh] <pas d'arguments>");
        } else {
            String scenario = ChatColor.GOLD + "---------------------" +ChatColor.DARK_RED + "[Scenarios]" + ChatColor.GOLD + "---------------------\n";
            String listeScenarios = "Voici une liste des scenarios avec leur descriptif :\n"
                    + "-" + ChatColor.DARK_RED + "TEAMS" + ChatColor.GOLD + " : pour une partie en équipes, désactivé si NOTEAMS est activé\n"
                    + "-" + ChatColor.DARK_RED + "NOTEAMS" + ChatColor.GOLD + " : pour une partie en Free For All, activé de base et désactivé si TEAMS est activé\n"
                    + "-" + ChatColor.DARK_RED + "FRIENDLYFIRE" + ChatColor.GOLD + " : permet aux joueurs d'une même équipe de se blesser, désactivé et activable uniquement si TEAMS activé\n"
                    + "-" + ChatColor.DARK_RED + "TIMBER" + ChatColor.GOLD + " : coupe l'arbre entier dès qu'une buche est cassée, désactivé de base\n"
                    + "-" + ChatColor.DARK_RED + "CUTCLEAN" + ChatColor.GOLD + " : drop directement le lingot/le résultat en double quand le minerai est cassé, désactivé de base\n"
                    + "-" + ChatColor.DARK_RED + "HASTEYBOYS" + ChatColor.GOLD + " : enchante les outils avec efficiency 3 et unbreaking 3 au craft, désactivé de base\n";
            player.sendMessage(scenario + listeScenarios + "\n" + scenario);
        }
    }
}
