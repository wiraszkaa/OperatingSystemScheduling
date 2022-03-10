package Zad1;

import Zad1.PlanningAlgorithms.Amount;
import Zad1.PlanningAlgorithms.FCFS;
import Zad1.PlanningAlgorithms.ProcessesBuilder;
import Zad1.PlanningAlgorithms.SJF;

import javax.management.InvalidAttributeValueException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidAttributeValueException {
        ProcessesBuilder processesBuilder = new ProcessesBuilder(7000);
        processesBuilder.segmentsNumber(2);
        processesBuilder.processesBTinSegments(List.of(Amount.HIGH, Amount.HIGH));
        processesBuilder.processesNumberInSegments(List.of(Amount.HIGH, Amount.LOW));
        processesBuilder.processesDensityInSegments(List.of(Amount.MEDIUM, Amount.MEDIUM));
        Simulation simulation = new Simulation(processesBuilder);
        simulation.start(new FCFS());
        simulation.start(new SJF());
    }
}
