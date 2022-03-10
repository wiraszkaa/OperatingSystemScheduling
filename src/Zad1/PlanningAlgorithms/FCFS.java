package Zad1.PlanningAlgorithms;

import java.util.List;

public class FCFS implements PlanningAlgorithm {

    @Override
    public void start(List<SystemProcess> processes) {
        System.out.println("\nStarting FCFS...\n");
        SystemProcess first = processes.get(0);
        System.out.println("Starting " + first.name + " with burstTime= " + first.burstTime + " and waitingTime= " + first.waitingTime);
        for(int i = 1; i < processes.size(); i++) {
            SystemProcess prev = processes.get(i - 1);
            SystemProcess curr = processes.get(i);
            curr.waitingTime = Math.max(prev.completionTime - curr.arrivalTime, 0);
            curr.completionTime = curr.arrivalTime + curr.waitingTime + curr.burstTime;
            curr.turnaroundTime = curr.completionTime - curr.arrivalTime;
            System.out.println("Starting " + curr.name + " with burstTime= " + curr.burstTime + " and waitingTime= " + curr.waitingTime);
        }
        double avgWaitingTime = processes.stream().mapToDouble((process) -> process.waitingTime).sum() / processes.size();
        System.out.println("Average waitingTime= " + avgWaitingTime);
    }
}
