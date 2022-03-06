package Zad1;

import java.util.List;

public class FCFS implements PlanningAlgorithm {
    int averageWaitingTime;

    public void start(List<SystemProcess> processes) {
        int waitingTimeSum = 0;
        int currentState = 0;
        SystemProcess currentProcess = processes.get(0);
        System.out.println("Starting " + currentProcess.name + " with burstTime= " + currentProcess.burstTime + " and waitingTime= " + currentProcess.waitingTime);
        while(true) {
            if(currentProcess.burstTime + currentProcess.waitingTime == currentState) {
                try {
                    currentProcess = processes.get(currentProcess.number + 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("All processes finished");
                    break;
                }
                currentProcess.waitingTime = currentState;
                waitingTimeSum += currentProcess.waitingTime;
                System.out.println("Starting " + currentProcess.name + " with burstTime= " + currentProcess.burstTime + " and waitingTime= " + currentProcess.waitingTime);
            }
            currentState++;
        }
        averageWaitingTime = waitingTimeSum / processes.size();
        System.out.println("Average waitingTime= " + averageWaitingTime);
    }
}
