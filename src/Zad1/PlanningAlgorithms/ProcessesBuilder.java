package Zad1.PlanningAlgorithms;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class ProcessesBuilder {
    private int lastProcessID;
    private int lastProcessArrival;

    private final int processesAmount;
    private int segments;
    private double midPercentage;
    private List<Amount> BTinSegments;
    private List<Amount> processesInSegments;
    private final HashMap<Amount, int[]> limits;

    private final ArrayList<SystemProcess> processes;

    public ProcessesBuilder(int processesAmount) throws InvalidAttributeValueException {
        if(processesAmount < 0) {
            throw new InvalidAttributeValueException();
        }
        lastProcessID = 0;
        lastProcessArrival = 0;

        this.processesAmount = processesAmount;
        segments = 3;
        midPercentage = 0.3;
        BTinSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        processesInSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        limits = new HashMap<>();
        limits.put(Amount.LOW, new int[]{0, 10});
        limits.put(Amount.MEDIUM, new int[]{10, 20});
        limits.put(Amount.HIGH, new int[]{20, 100});

        processes = new ArrayList<>();
    }

    public boolean segmentsNumber(int amount) {
        if(amount > 0) {
            this.segments = amount;
            System.out.println("Remember about changing BTinSegments and processesInSegments length to " + amount + " for the builder to work");
            return true;
        }
        return false;
    }

    public boolean midPercentage(double percentage) {
        if (percentage > 0 && percentage < 1) {
            this.midPercentage = percentage;
            return true;
        }
        return false;
    }

    public boolean BTinSegments(List<Amount> burstTime) {
        if(burstTime.size() == segments) {
            this.BTinSegments = burstTime;
            return true;
        }
        return false;
    }

    public boolean processesInSegments(List<Amount> processes) {
        if(processes.size() == segments) {
            this.processesInSegments = processes;
            return true;
        }
        return false;
    }

    public boolean BTLimits(Amount amount, int low, int high) {
        if(low < high) {
            limits.put(amount, new int[]{low, high});
            return true;
        }
        return false;
    }

    private void createSegment(int number, Amount BT) {
        int low = limits.get(BT)[0];
        int high = limits.get(BT)[1];
        for(int i = 0; i < number; i++) {
            SystemProcess process = new SystemProcess(
                    "Process" + lastProcessID,
                    lastProcessID,
                    rand(low, high),
                    lastProcessArrival + rand(0, 10));
            processes.add(process);
            lastProcessID++;
            lastProcessArrival = process.arrivalTime;
        }
    }

    private int rand(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    public ArrayList<SystemProcess> create() throws InvalidAttributeValueException {
        if(BTinSegments.size() != segments || processesInSegments.size() != segments) {
            throw new InvalidAttributeValueException();
        }
        int lowSegments = Collections.frequency(processesInSegments, Amount.LOW);
        int mediumSegments = Collections.frequency(processesInSegments, Amount.MEDIUM);
        int highSegments = Collections.frequency(processesInSegments, Amount.HIGH);
        double perChange = 0.3;
        if(lowSegments != highSegments) {
            perChange = (1 - midPercentage * (lowSegments + mediumSegments + highSegments)) / (highSegments - lowSegments);
        } else {
            midPercentage = 1.0 / (2 * lowSegments + mediumSegments);
        }
        for (int i = 0; i < segments; i++) {
            double percentage = midPercentage;
            switch (processesInSegments.get(i)) {
                case LOW -> percentage -= perChange;
                case HIGH -> percentage += perChange;
            }
            int number = (int) (processesAmount * percentage);
            if(i == segments - 1) {
                int addition = processesAmount - lastProcessID - number;
                if(addition != 0) {
                    number += addition;
                }
            }
            createSegment(number, BTinSegments.get(i));
        }
        return processes;
    }

    public ArrayList<SystemProcess> createCopy() {
        ArrayList<SystemProcess> copy = new ArrayList<>();
        for(SystemProcess process: processes) {
            copy.add(new SystemProcess(process.name, process.id, process.burstTime, process.arrivalTime));
        }
        return copy;
    }
}
