package Zad1.PlanningAlgorithms;

public class SystemProcess {
    String name;
    final int id;

    public final int burstTime;
    int completionLevel;

    final int arrivalTime;
    public int waitingTime;
    public int completionTime;
    public int turnaroundTime;

    public SystemProcess(String name, int id, int burstTime, int arrivalTime) {
        this.name = name;
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        waitingTime = 0;
        turnaroundTime = 0;
        completionTime = arrivalTime + waitingTime + burstTime;
        completionLevel = 0;
    }
}
