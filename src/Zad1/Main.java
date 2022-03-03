package Zad1;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.addProcesses(20);
        simulation.startFCFS(PlanningAlgorithm.FCFS);
    }
}
