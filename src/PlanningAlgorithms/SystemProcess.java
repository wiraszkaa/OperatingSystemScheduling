package PlanningAlgorithms;

public class SystemProcess {
    public String name;
    public final int id;

    public final int burstTime;
    public int completionLevel;

    public final int arrivalTime;
    public double waitingTime;
    public double completionTime;
    public double turnaroundTime;

    public SystemProcess(String name, int id, int burstTime, int arrivalTime) {
        this.name = name;
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        waitingTime = 0;
        turnaroundTime = 0;
        completionLevel = 0;
        completionTime = arrivalTime + burstTime;
    }

    public void setCompletionTime(int completionTime) { //FOR RR
        this.completionTime = completionTime;
        turnaroundTime = completionTime - arrivalTime;
        waitingTime = (waitingTime + completionTime) / 2;
    }
}
