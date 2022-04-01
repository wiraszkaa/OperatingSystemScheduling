import PlanningAlgorithms.PlanningAlgorithm;
import PlanningAlgorithms.ProcessesBuilder;
import PlanningAlgorithms.SystemProcess;

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
        long finish = (System.nanoTime() - start);
        System.out.println("Simulation took " + ElapsedTimeString.getTime(finish));

        double avgWT = processes.stream().mapToDouble((process) -> process.waitingTime).sum() / processes.size();
        System.out.println("Average waitingTime= " + avgWT);
        
        OptionalDouble maxWT = processes.stream().mapToDouble((process) -> process.waitingTime).max();
        String maxWaitingTime = "Not Defined";
        if(maxWT.isPresent()) {
            maxWaitingTime = String.valueOf(maxWT.getAsDouble());
        }
        System.out.println("Maximum waitingTime= " + maxWaitingTime);

        double avgTT = processes.stream().mapToDouble((process) -> process.turnaroundTime).sum() / processes.size();
        System.out.println("Average turnaroundTime= " + avgTT);

        OptionalDouble maxTT = processes.stream().mapToDouble((process) -> process.turnaroundTime).max();
        String maxTurnaroundTime = "Not Defined";
        if(maxTT.isPresent()) {
            maxTurnaroundTime = String.valueOf(maxTT.getAsDouble());
        }
        System.out.println("Maximum turnaroundTime= " + maxTurnaroundTime);

        OptionalDouble maxCT = processes.stream().mapToDouble((process) -> process.completionTime).max();
        String maxCompletionTime = "Not Defined";
        if(maxCT.isPresent()) {
            maxCompletionTime = String.valueOf(maxCT.getAsDouble());
        }

        System.out.println("Process switches= " + algorithm.getProcessSwitches());

        System.out.println("All Processes executionTime= " + maxCompletionTime);
    }
}
