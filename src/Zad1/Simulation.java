package Zad1;

import Zad1.PlanningAlgorithms.PlanningAlgorithm;
import Zad1.PlanningAlgorithms.ProcessesBuilder;
import javax.management.InvalidAttributeValueException;

public class Simulation {
    private final ProcessesBuilder processesBuilder;

    public Simulation(ProcessesBuilder processesBuilder) throws InvalidAttributeValueException {
        this.processesBuilder = processesBuilder;
        processesBuilder.create();
    }

    public void start(PlanningAlgorithm algorithm) {
        algorithm.start(processesBuilder.createCopy());
    }
}
