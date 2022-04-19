package fr.timeuh.scenario;

import java.util.ArrayList;
import java.util.List;

public class ScenarList {

    private List<Scenario> allScenarios;
    private List<Scenario> activeScenarios;

    public ScenarList() {
        this.activeScenarios = new ArrayList<>();
        this.allScenarios = createAllScenarios();
    }

    private List<Scenario> createAllScenarios(){
        List<Scenario> scenarios = new ArrayList<>();
        scenarios.add(Scenario.TEAMS);
        scenarios.add(Scenario.NOTEAMS);
        scenarios.add(Scenario.FRIENDLYFIRE);
        scenarios.add(Scenario.TIMBER);
        scenarios.add(Scenario.HASTEYBOYS);
        scenarios.add(Scenario.CUTCLEAN);
        return scenarios;
    }

    public void enableScenario(Scenario scenario){
        if (allScenarios.contains(scenario)){
            activeScenarios.add(scenario);
        }
    }

    public void disableScenario(Scenario scenario){
        if (allScenarios.contains(scenario)){
            if (activeScenarios.contains(scenario)) activeScenarios.remove(scenario);
        }
    }

    public List<Scenario> getActiveScenarios() {
        return activeScenarios;
    }

    public List<Scenario> getAllScenarios() {
        return allScenarios;
    }

    public boolean isEnabled(Scenario scenario){
        return activeScenarios.contains(scenario);
    }

    public String getScenarioName(Scenario scenario){
        if (allScenarios.contains(scenario)){
            switch (scenario){
                case TEAMS:
                    return "Teams";
                case NOTEAMS:
                    return "No Teams";
                case FRIENDLYFIRE:
                    return "Friendly Fire";
                case TIMBER:
                    return "Timber";
                case CUTCLEAN:
                    return "Cutclean";
                case HASTEYBOYS:
                    return "Hastey Boys";
                default:
                    return "";
            }
        }
        return "";
    }
}
