package PlanningAlgorithms;

import javax.management.InvalidAttributeValueException;
import java.util.*;

public class ProcessesBuilder {
    private int lastProcessID;
    private int lastProcessArrival;

    private final int processesAmount;
    private int segments;
    private List<Amount> BTinSegments;
    private List<Amount> densityInSegments;
    private final HashMap<Amount, int[]> BTlimits;
    private final HashMap<Amount, int[]> densityLimits;
    private boolean manual;

    private final ArrayList<SystemProcess> processes;

    public ProcessesBuilder(int processesAmount) throws InvalidAttributeValueException {
        if(processesAmount < 0) {
            throw new InvalidAttributeValueException();
        }
        lastProcessID = 0;
        lastProcessArrival = 0;

        this.processesAmount = processesAmount;
        segments = 3;
        BTinSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        densityInSegments = new ArrayList<>(Arrays.asList(Amount.MEDIUM, Amount.MEDIUM, Amount.MEDIUM));
        BTlimits = new HashMap<>();
        BTlimits.put(Amount.LOW, new int[]{0, 10});
        BTlimits.put(Amount.MEDIUM, new int[]{10, 20});
        BTlimits.put(Amount.HIGH, new int[]{20, 100});
        densityLimits = new HashMap<>();
        densityLimits.put(Amount.LOW, new int[]{10, 50});
        densityLimits.put(Amount.MEDIUM, new int[]{3, 10});
        densityLimits.put(Amount.HIGH, new int[]{0, 3});
        manual = false;

        processes = new ArrayList<>();
    }

    public boolean segmentsNumber(int amount) {
        if(amount > 0) {
            this.segments = amount;
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

    public void manualMode(List<Integer> list) {
        System.out.println("Manually creating " + processesAmount + " processes...");
        lastProcessID = 0;
        for(int i = 1; i < list.size(); i += 2) {
            processes.add(new SystemProcess("Process" + lastProcessID, lastProcessID, list.get(i), list.get(i - 1)));
        }
        manual = true;
    }

    private int rand(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    public void create() throws InvalidAttributeValueException {
        if(!manual) {
            System.out.println("Creating " + processesAmount + " processes...");
            lastProcessID = 0;

            if (BTinSegments.size() != segments || densityInSegments.size() != segments) {
                throw new InvalidAttributeValueException();
            }

            for (int i = 0; i < segments; i++) {
                int number = (int) (processesAmount * (1.0 / segments));
                if (i == segments - 1) {
                    int addition = processesAmount - lastProcessID - number;
                    if (addition != 0) {
                        number += addition;
                    }
                }
                createSegment(number, BTinSegments.get(i), densityInSegments.get(i));
            }
        }
        System.out.println(this);
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
                "\nBTinSegments=" + BTinSegments +
                "\ndensityInSegments=" + densityInSegments +
                "\nBTlimits=" + limitsToString(BTlimits) +
                "\ndensityLimits=" + limitsToString(densityLimits);
    }
}
