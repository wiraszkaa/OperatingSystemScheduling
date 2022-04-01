package PlanningAlgorithms.RR;

import PlanningAlgorithms.PlanningAlgorithm;
import PlanningAlgorithms.SystemProcess;

import java.util.List;

public class RR implements PlanningAlgorithm {
    private double quantum;
    private int processSwitches;

    public RR(double quantum) {
        this.quantum = quantum;
        processSwitches = 0;
    }

    @Override
    public List<SystemProcess> start(List<SystemProcess> processes) {
        System.out.println("\nStarting RR...\n");
        int completed = 0;
        int currentTime = processes.get(0).arrivalTime;
        while (completed < processes.size()) {
            for (SystemProcess process : processes) {
                if (process.arrivalTime <= currentTime && process.completionLevel < process.burstTime) {
                    processSwitches++;
                    process.completionLevel += quantum;
                    currentTime += quantum;
                    double rest = process.completionLevel - process.burstTime;
                    if (rest >= 0) {
                        completed++;
                        process.completionLevel -= rest;
                        currentTime -= rest;
                        process.setCompletionTime(currentTime);
                    } else if (rest == -process.burstTime + quantum) {
                        process.waitingTime = currentTime - quantum - process.arrivalTime;
                    }
                }
            }
        }
        return processes;
    }

    @Override
    public int getProcessSwitches() {
        return processSwitches;
    }
}
