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
}
