package Zad1.PlanningAlgorithms;

import java.util.List;

public interface PlanningAlgorithm {
    int getProcessSwitches();
    List<SystemProcess> start(List<SystemProcess> processes);
}
