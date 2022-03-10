package Zad1.PlanningAlgorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SJF implements PlanningAlgorithm {

    @Override
    public List<SystemProcess> start(List<SystemProcess> processes) {
        System.out.println("\nStarting SJF...\n");
        int currentTime = processes.get(0).arrivalTime;
        List<SystemProcess> currentProcesses = new ArrayList<>();
        SystemProcess prev;
        SystemProcess curr = processes.get(0);
        System.out.println("Starting " + curr.name + " with burstTime= " + curr.burstTime + " and waitingTime= " + curr.waitingTime);
        boolean flag = false;
        while(true) {
            int finalCurrentTime = currentTime;
            currentProcesses.addAll(processes.stream()
                    .filter((process) -> process.arrivalTime <= finalCurrentTime && process.completionLevel < process.burstTime)
                    .collect(Collectors.toList()));
            if(currentProcesses.size() == 0) {
                break;
            }
            currentProcesses = currentProcesses.stream()
                    .distinct()
                    .sorted(Comparator.comparingInt((process) -> process.burstTime))
                    .collect(Collectors.toList());
            prev = curr;
            curr = currentProcesses.get(0);
            curr.waitingTime = Math.max(prev.completionTime - curr.arrivalTime, 0);
            if(!flag) {
                curr.waitingTime = 0;
                flag = true;
            }
            curr.completionTime = curr.arrivalTime + curr.waitingTime + curr.burstTime;
            curr.turnaroundTime = curr.completionTime - curr.arrivalTime;
            curr.completionLevel = curr.burstTime;
            currentTime = curr.completionTime;
            currentProcesses.remove(curr);
//            System.out.println("Starting " + curr.name + " with burstTime= " + curr.burstTime + " and waitingTime= " + curr.waitingTime);
        }
        return processes;
    }
}
