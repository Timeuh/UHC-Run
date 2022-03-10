package fr.timeuh.uhcrun.commands;

import fr.timeuh.uhcrun.UHCRun;
import fr.timeuh.uhcrun.scenarios.Scenarios;
import fr.timeuh.uhcrun.tasks.GameStart;
import fr.timeuh.uhcrun.tasks.GameStop;
import fr.timeuh.uhcrun.teams.PlayerTeams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GameCommands implements CommandExecutor {

    private UHCRun uhcRun;
    private PlayerTeams teams;

    public GameCommands(UHCRun uhcRun, PlayerTeams teams) {
        this.uhcRun = uhcRun;
        this.teams = teams;
    }

    public GameCommands() {
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            Location spawn = new Location(player.getWorld(), 0, 100, 0);

            switch(command.getName()){
                case "broadcast":
                    if(args.length == 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/broadcast, bc]" + ChatColor.GOLD + " <message>");
                        return false;
                    } else {
                        StringBuilder broadcast = new StringBuilder();
                        for (String partie : args){
                            broadcast.append(partie + " ");
                        }
                        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.DARK_RED + "MESSAGE : " + ChatColor.GOLD + broadcast);
                        return true;
                    }

                case "spawn":
                    if (args.length > 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/spawn]");
                        return false;
                    } else {
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Téléportation au spawn...");
                        player.teleport(spawn);
                        return true;
                    }

                case "gamestop":
                    if (args.length > 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/gamestop]");
                        return false;
                    } else {
                        GameStop stop = new GameStop(uhcRun, teams);
                        stop.runTaskTimer(uhcRun, 0, 20);
                        return true;
                    }

                case "start":
                    if (args.length > 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/start]");
                        return false;
                    } else {
                        if (uhcRun.everyPlayerInTeam()) {
                            GameStart start = new GameStart(uhcRun, teams);
                            start.runTaskTimer(uhcRun, 0, 20);
                            return true;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Tous les joueurs doivent etre dans une equipe");
                            return false;
                        }
                    }

                case "help":
                    if (args.length > 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/help]");
                        return false;
                    } else {
                        String displayHelp = ChatColor.DARK_PURPLE + "[HELP]" + ChatColor.GOLD + " Bienvenue dans ce mode de jeu UHC Run !\nSi vous ne savez pas ce que c'est, le principe est simple : " +
                                "vous avez 20 minutes pour faire votre stuff, sachant que le spawn de minerais est plus frequent et les couches sont adaptees, tout comme certains loots. Au bout de 20 minutes" +
                                " tous les joueurs sont tp et la phase de pvp commence : le but etant d'etre le dernier joueur/team survivant\n" + ChatColor.DARK_RED + "Voici une liste des commandes : "
                                + ChatColor.GOLD + "(le symbole " + ChatColor.DARK_PURPLE + "[OP]" + ChatColor.GOLD + " signife que la commande est disponible" + " uniquement pour les joueurs op)\n" +
                                ChatColor.DARK_RED + "/help" + ChatColor.GOLD + " - affiche l'aide (vous etes dessus)\n" + ChatColor.DARK_RED + "/seeEnabledScenarios" + ChatColor.GOLD +
                                " - permet de voir les scenarios actifs ou non\n" + ChatColor.DARK_PURPLE + "[OP]" + ChatColor.DARK_RED + " /broadcast" + ChatColor.GOLD + " - vous permet de faire une annonce\n" +
                                ChatColor.DARK_PURPLE + "[OP]" + ChatColor.DARK_RED + "/spawn " + ChatColor.GOLD + "- vous teleporte au 0 0\n" + ChatColor.DARK_PURPLE + "[OP]" + ChatColor.DARK_RED +
                                " /gamestop" + ChatColor.GOLD + " - arrete la partie et teleporte tous les joueurs au spawn\n" + ChatColor.DARK_PURPLE + "[OP]" + ChatColor.DARK_RED + " /start" +
                                ChatColor.GOLD + " - lance la partie";
                        player.sendMessage(displayHelp);
                        return true;
                    }

                case "seeEnabledScenarios":
                    if (args.length > 0){
                        player.sendMessage(ChatColor.DARK_PURPLE + "[UHCRun] " + ChatColor.GOLD + "Usage : " + ChatColor.DARK_RED + "[/seeEnabledScenarios, scenarios, ses]");
                        return false;
                    } else {
                        String display = ChatColor.GOLD + "Voici la liste des scenarios :";
                        String name = "";
                        List<Scenarios> allScenarios = uhcRun.getAllScenarios();
                        for (Scenarios scenario : allScenarios) {
                            for (Scenarios secondScenario : uhcRun.getEnabledScenarios()) {
                                if (scenario.equals(secondScenario)) {
                                    name = ChatColor.DARK_PURPLE + "Scenario " + ChatColor.GOLD + uhcRun.getScenarioName(scenario) + ChatColor.GREEN + " √";
                                } else {
                                    name = ChatColor.DARK_PURPLE + "Scenario " + ChatColor.GOLD + uhcRun.getScenarioName(scenario) + ChatColor.RED + " ✗";
                                }
                                display += "\n" + name;
                            }
                        }
                        player.sendMessage(display);
                        return true;
                    }

                default: break;
            }
        }
        return false;
    }
}
