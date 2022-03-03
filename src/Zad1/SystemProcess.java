package Zad1;

public class SystemProcess {
    String name;
    int number;
    int waitingTime = 0;
    int burstTime;

    public SystemProcess(String name, int number, int burstTime) {
        this.name = name;
        this.number = number;
        this.burstTime = burstTime;
    }
}
