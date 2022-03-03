package Zad1;

import java.util.List;

public enum PlanningAlgorithm {
    FCFS, SJF, ROTATIONAL;

    public void start(List<SystemProcess> processes) {
        switch (this) {
            case FCFS -> {
                System.out.println("STARTING FCFS...");
                int burstTimeSum = 0;
                int waitingTimeSum = 0;
                int currentState = 0;
                SystemProcess currentProcess = processes.get(0);
                System.out.println("Starting " + currentProcess.name + " with burstTime= " + currentProcess.burstTime + " and waitingTime= " + currentProcess.waitingTime);
                while(true) {
                    if(currentProcess.burstTime + currentProcess.waitingTime == currentState) {
                        try {
                            burstTimeSum += currentProcess.burstTime;
                            currentProcess = processes.get(currentProcess.number + 1);
                        } catch (Exception e) {
                            break;
                        }
                        currentProcess.waitingTime = currentState;
                        waitingTimeSum += currentProcess.waitingTime;
                        System.out.println("Starting " + currentProcess.name + " with burstTime= " + currentProcess.burstTime + " and waitingTime= " + currentProcess.waitingTime);
                    }
                    currentState++;
                }
                System.out.println("Average burstTime= " + (burstTimeSum / processes.size()));
                System.out.println("Average waitingTime= " + (waitingTimeSum / processes.size()));
            }
            case SJF -> {
                System.out.println("STARTING SJF...");

            }
            case ROTATIONAL -> {
                System.out.println("STARTING ROTATIONAL...");
            }
        }
    }
}
