package Zad1.PlanningAlgorithms.RR;

import Zad1.PlanningAlgorithms.PlanningAlgorithm;
import Zad1.PlanningAlgorithms.ProcessAppearingHelper;
import Zad1.PlanningAlgorithms.SystemProcess;

import java.util.LinkedList;
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
        int lastProcess = 0;
        int completed = 0;
        int currentTime = processes.get(0).arrivalTime;
        LinkedList<SystemProcess> currentProcesses = new LinkedList<>();
        LinkedList<SystemProcess> temp = new LinkedList<>();
        while(completed < processes.size()) {
            lastProcess = ProcessAppearingHelper.addProcesses(processes, currentProcesses, lastProcess, currentTime);
            for(SystemProcess process: currentProcesses) {
                process.completionLevel += quantum;
                currentTime += quantum;
                processSwitches++;
                double rest = process.completionLevel - process.burstTime;
                if(rest >= 0) {
                    completed++;
                    process.completionLevel -= rest;
                    currentTime -= rest;
                    process.setCompletionTime(currentTime);
                    temp.add(process);
                } else if(rest == -process.burstTime + quantum) {
                    process.waitingTime = (int) (currentTime - quantum - process.arrivalTime);
                }
            }
            currentProcesses.removeAll(temp);
        }
        return processes;
    }

    @Override
    public int getProcessSwitches() {
        return processSwitches;
    }
}
