package Zad1;

import Zad1.PlanningAlgorithms.PlanningAlgorithm;
import Zad1.PlanningAlgorithms.ProcessesBuilder;
import Zad1.PlanningAlgorithms.SystemProcess;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class Simulation {
    private final ProcessesBuilder processesBuilder;

    public Simulation(ProcessesBuilder processesBuilder) throws InvalidAttributeValueException {
        this.processesBuilder = processesBuilder;
        processesBuilder.create();
    }

    public void start(PlanningAlgorithm algorithm) {
        long start = System.nanoTime();
        List<SystemProcess> processes = algorithm.start(processesBuilder.createCopy());
        long finish = (System.nanoTime() - start) / 1000000;
        System.out.println("\nSimulation took " + finish + " ms");

        double avgWT = processes.stream().mapToDouble((process) -> process.waitingTime).sum() / processes.size();
        System.out.println("Average waitingTime= " + avgWT);
        
        OptionalDouble maxWT = processes.stream().mapToDouble((process) -> process.waitingTime).max();
        String maxWaitingTime = "Not Defined";
        if(maxWT.isPresent()) {
            maxWaitingTime = String.valueOf(maxWT.getAsDouble());
        }
        System.out.println("Maximum waitingTime= " + maxWaitingTime);

        OptionalInt maxCT = processes.stream().mapToInt((process) -> process.completionTime).max();
        String maxCompletionTime = "Not Defined";
        if(maxCT.isPresent()) {
            maxCompletionTime = String.valueOf(maxCT.getAsInt());
        }
        System.out.println("Processes execution= " + maxCompletionTime);
    }
}
