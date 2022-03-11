package Zad1.PlanningAlgorithms.SJF;

import Zad1.PlanningAlgorithms.PlanningAlgorithm;
import Zad1.PlanningAlgorithms.ProcessAppearingHelper;
import Zad1.PlanningAlgorithms.SystemProcess;

import java.util.*;

public class SJF implements PlanningAlgorithm {
    private int processSwitches;

    @Override
    public List<SystemProcess> start(List<SystemProcess> processes) {
        System.out.println("\nStarting SJF...\n");
        double currentTime = processes.get(0).arrivalTime;
        int lastProcess = 0;
        int completed = 0;
        TreeSet<SystemProcess> currentProcesses = new TreeSet<>(new SJFSorting());
        SystemProcess prev;
        SystemProcess curr = processes.get(0);
        curr.completionTime = curr.arrivalTime;
        while(completed < processes.size()) {
            lastProcess = ProcessAppearingHelper.addProcesses(processes, currentProcesses, lastProcess, currentTime);
            if(currentProcesses.size() > 0) {
                prev = curr;
                curr = currentProcesses.first();
                curr.waitingTime = Math.max(prev.completionTime - curr.arrivalTime, 0);
                curr.completionTime = curr.arrivalTime + curr.waitingTime + curr.burstTime;
                curr.turnaroundTime = curr.completionTime - curr.arrivalTime;
                curr.completionLevel = curr.burstTime;
                currentTime = curr.completionTime;
                currentProcesses.remove(curr);
                completed++;
            } else {
                if(lastProcess < processes.size()) {
                    currentTime = processes.get(lastProcess).arrivalTime;
                }
            }
        }
        processSwitches = processes.size();
        return processes;
    }

    @Override
    public int getProcessSwitches() {
        return processSwitches;
    }
}
