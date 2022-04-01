package PlanningAlgorithms.SJF;

import PlanningAlgorithms.PlanningAlgorithm;
import PlanningAlgorithms.SystemProcess;

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
            lastProcess = addProcesses(processes, currentProcesses, lastProcess, currentTime);
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

    private static int addProcesses(List<SystemProcess> processes, Collection<SystemProcess> currentProcesses, int lastProcess, double currentTime) {
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

    @Override
    public int getProcessSwitches() {
        return processSwitches;
    }
}
