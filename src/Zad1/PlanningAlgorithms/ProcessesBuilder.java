package Zad1.PlanningAlgorithms;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class ProcessesBuilder {
    private int lastProcessID;
    private int lastProcessArrival;

    private final int processesAmount;
    private int segments;
    private double midPercentage;
    private double percentageChange;
    private List<Amount> BTinSegments;
    private List<Amount> processesInSegments;
    private List<Amount> densityInSegments;
    private final HashMap<Amount, int[]> BTlimits;
    private final HashMap<Amount, int[]> densityLimits;

    private final ArrayList<SystemProcess> processes;

    public ProcessesBuilder(int processesAmount) throws InvalidAttributeValueException {
        if(processesAmount < 0) {
            throw new InvalidAttributeValueException();
        }
        lastProcessID = 0;
        lastProcessArrival = 0;

        this.processesAmount = processesAmount;
        segments = 3;
        midPercentage = 0.4;
        percentageChange = 0.3;
        BTinSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        processesInSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        densityInSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        BTlimits = new HashMap<>();
        BTlimits.put(Amount.LOW, new int[]{0, 10});
        BTlimits.put(Amount.MEDIUM, new int[]{10, 20});
        BTlimits.put(Amount.HIGH, new int[]{20, 100});
        densityLimits = new HashMap<>();
        densityLimits.put(Amount.LOW, new int[]{10, 50});
        densityLimits.put(Amount.MEDIUM, new int[]{3, 10});
        densityLimits.put(Amount.HIGH, new int[]{0, 3});

        processes = new ArrayList<>();
    }

    public boolean segmentsNumber(int amount) {
        if(amount > 0) {
            this.segments = amount;
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

    public boolean processesBTinSegments(List<Amount> burstTime) {
        if(burstTime.size() == segments) {
            this.BTinSegments = burstTime;
            return true;
        }
        return false;
    }

    public boolean processesNumberInSegments(List<Amount> processes) {
        if(processes.size() == segments) {
            this.processesInSegments = processes;
            return true;
        }
        return false;
    }

    public boolean processesDensityInSegments(List<Amount> processes) {
        if(processes.size() == segments) {
            this.densityInSegments = processes;
            return true;
        }
        return false;
    }

    public boolean BTLimits(Amount amount, int low, int high) {
        if(low < high) {
            BTlimits.put(amount, new int[]{low, high});
            return true;
        }
        return false;
    }

    public boolean densityLimits(Amount amount, int low, int high) {
        if(low < high) {
            densityLimits.put(amount, new int[]{low, high});
            return true;
        }
        return false;
    }

    private void createSegment(int number, Amount BT, Amount density) {
        int lowBT = BTlimits.get(BT)[0];
        int highBT = BTlimits.get(BT)[1];
        int lowDensity = densityLimits.get(density)[0];
        int highDensity = densityLimits.get(density)[1];
        for(int i = 0; i < number; i++) {
            SystemProcess process = new SystemProcess(
                    "Process" + lastProcessID,
                    lastProcessID,
                    rand(lowBT, highBT),
                    lastProcessArrival + rand(lowDensity, highDensity));
            processes.add(process);
            lastProcessID++;
            lastProcessArrival = process.arrivalTime;
        }
    }

    private int rand(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    public ArrayList<SystemProcess> create() throws InvalidAttributeValueException {
        System.out.println("Creating " + processesAmount + " processes...");

        if(BTinSegments.size() != segments || processesInSegments.size() != segments || densityInSegments.size() != segments) {
            throw new InvalidAttributeValueException();
        }
        int lowSegments = Collections.frequency(processesInSegments, Amount.LOW);
        int mediumSegments = Collections.frequency(processesInSegments, Amount.MEDIUM);
        int highSegments = Collections.frequency(processesInSegments, Amount.HIGH);
        if(lowSegments != highSegments) {
            percentageChange = (1 - midPercentage * (lowSegments + mediumSegments + highSegments)) / (highSegments - lowSegments);
        } else {
            midPercentage = 1.0 / (2 * lowSegments + mediumSegments);
        }
        for (int i = 0; i < segments; i++) {
            double percentage = midPercentage;
            switch (processesInSegments.get(i)) {
                case LOW -> percentage -= percentageChange;
                case HIGH -> percentage += percentageChange;
            }
            int number = (int) (processesAmount * percentage);
            if(i == segments - 1) {
                int addition = processesAmount - lastProcessID - number;
                if(addition != 0) {
                    number += addition;
                }
            }
            createSegment(number, BTinSegments.get(i), densityInSegments.get(i));
        }
        System.out.println(this);
        return processes;
    }

    public ArrayList<SystemProcess> createCopy() {
        ArrayList<SystemProcess> copy = new ArrayList<>();
        for(SystemProcess process: processes) {
            copy.add(new SystemProcess(process.name, process.id, process.burstTime, process.arrivalTime));
        }
        return copy;
    }

    private String limitsToString(HashMap<Amount, int[]> limits) {
        StringBuilder sb = new StringBuilder("{");
        for(Amount key: limits.keySet()) {
            int[] ints = limits.get(key);
            sb
            .append(key)
            .append("=[")
            .append(ints[0])
            .append(", ")
            .append(ints[1])
            .append("] ");
        }
        sb.deleteCharAt(sb.lastIndexOf(" "));
        sb.append("}");
        return sb.toString();
    }

    public int getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return "Parameters:" +
                "\nsegments=" + segments +
                "\nmidPercentage=" + (midPercentage * 100) + "%" +
                "\npercentageChange=" + (percentageChange * 100) + "%" +
                "\nBTinSegments=" + BTinSegments +
                "\nprocessesInSegments=" + processesInSegments +
                "\ndensityInSegments=" + densityInSegments +
                "\nBTlimits=" + limitsToString(BTlimits) +
                "\ndensityLimits=" + limitsToString(densityLimits);
    }
}
