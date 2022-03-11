package Zad1.PlanningAlgorithms.SJF;

import Zad1.PlanningAlgorithms.SystemProcess;

import java.util.Comparator;

public class SJFSorting implements Comparator<SystemProcess> {
    @Override
    public int compare(SystemProcess o1, SystemProcess o2) {
        if(o1.burstTime < o2.burstTime) {
            return -1;
        } else if(o1.burstTime == o2.burstTime) {
            if(o1.arrivalTime < o2.arrivalTime) {
                return -1;
            } else if(o1.arrivalTime == o2.arrivalTime) {
                return Integer.compare(o1.id, o2.id);
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
