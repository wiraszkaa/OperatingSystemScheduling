package Zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private final List<SystemProcess> processes;
    private static int processNumber;

    public Simulation() {
        processNumber = -1;
        processes = new ArrayList<>();
    }

    public void start(PlanningAlgorithm algorithm) {
        if(processes.size() > 0) {
            algorithm.start(processes);
        } else {
            System.out.println("Can't start simulation without Processes");
            System.out.println("Create Processes manually with addProcess or automatically with addProcesses");
        }
    }

    public void addProcess(SystemProcess process) {
        processes.add(process);
    }

    private SystemProcess createRandomProcess() {
        Random random = new Random();
        processNumber++;
        return new SystemProcess("Process" + processNumber, processNumber, random.nextInt(30));
    }

    public void addProcesses(int numberOfProcesses) {
        for (int i = 0; i < numberOfProcesses; i++) {
            addProcess(createRandomProcess());
        }
    }
}
