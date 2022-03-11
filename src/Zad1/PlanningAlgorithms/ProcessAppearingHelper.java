package Zad1.PlanningAlgorithms;

import java.util.Collection;
import java.util.List;

public class ProcessAppearingHelper {

    public static int addProcesses(List<SystemProcess> processes, Collection<SystemProcess> currentProcesses, int lastProcess, double currentTime) {
        for(int i = lastProcess; i < processes.size(); i++) {
            SystemProcess process = processes.get(i);
            if(process.arrivalTime <= currentTime) {
                currentProcesses.add(process);
                lastProcess = process.id + 1;
            } else {
                break;
            }
        }
        return lastProcess;
    }
}
