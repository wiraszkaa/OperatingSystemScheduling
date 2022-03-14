package Zad1;

import Zad1.PlanningAlgorithms.Amount;
import Zad1.PlanningAlgorithms.FCFS.FCFS;
import Zad1.PlanningAlgorithms.ProcessesBuilder;
import Zad1.PlanningAlgorithms.RR.RR;
import Zad1.PlanningAlgorithms.SJF.SJF;

import javax.management.InvalidAttributeValueException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InvalidAttributeValueException {
        UI();
//        long start = System.nanoTime();
//        ProcessesBuilder processesBuilder = new ProcessesBuilder(15_000_000);
//        processesBuilder.segmentsNumber(2);
//        processesBuilder.processesBTinSegments(List.of(Amount.HIGH, Amount.HIGH));
//        processesBuilder.processesNumberInSegments(List.of(Amount.MEDIUM, Amount.MEDIUM));
//        processesBuilder.processesDensityInSegments(List.of(Amount.HIGH, Amount.HIGH));
//        Simulation simulation = new Simulation(processesBuilder);
//        simulation.start(new FCFS());
//        simulation.start(new SJF());
//        simulation.start(new RR(10));
//        long time = System.nanoTime() - start;
//        System.out.println("Finished in " + ElapsedTimeString.getTime(time));
    }

    public static void UI() throws InvalidAttributeValueException {
        System.out.println("WELCOME TO PLANNING ALGORITHM SIMULATOR");
        System.out.println("Start by choosing number of processes:");
        int input = userInteger();
        if (input > 0) {
            ProcessesBuilder processesBuilder = new ProcessesBuilder(input);
            boolean quit = false;
            while (!quit) {
                printOptions();
                String option = sc.nextLine();
                switch (option) {
                    case "1" -> System.out.println(processesBuilder);
                    case "2" -> {
                        showParametersMenu(processesBuilder);
                    }
                    case "3" -> startSimulation(processesBuilder);
                    default -> quit = true;
                }
            }
        } else {
            System.out.println("Number of processes can't be lower or equal to 0");
        }
    }

    private static void printOptions() {
        System.out.println("""
                Choose option:
                1 - Show Parameters
                2 - Change Parameters
                3 - Start Simulation
                ANY - Quit
                """);
    }

    private static void printParametersOptions() {
        System.out.println(
                """
                        Choose parameters:
                        1 - Segments Number
                        2 - Medium Percentage
                        3 - Processes BT in Segments
                        4 - Processes Number in Segments
                        5 - Processes Density in Segments
                        6 - BT limits
                        7 - Density limits
                        ANY - Back""");
    }

    private static void startSimulation(ProcessesBuilder processesBuilder) throws InvalidAttributeValueException {
        Simulation simulation = new Simulation(processesBuilder);
        boolean quit = false;
        while (!quit) {
            System.out.println("""
                    Choose Simulation:
                    1 - First Comes First Served
                    2 - Shortest Job First
                    3 - Round Robin
                    ANY - Back""");
            String option = sc.nextLine();
            switch (option) {
                case "1" -> simulation.start(new FCFS());
                case "2" -> simulation.start(new SJF());
                case "3" -> {
                    System.out.println("Choose quantum:");
                    int input = userInteger();
                    if (input > 0) {
                        simulation.start(new RR(input));
                    } else {
                        System.out.println("Wrong Input");
                    }
                }
                default -> quit = true;
            }
        }
    }

    private static void showParametersMenu(ProcessesBuilder processesBuilder) {
        boolean quit = false;
        while (!quit) {
            printParametersOptions();
            String option = sc.nextLine();
            switch (option) {
                case "1" -> chooseSegments(processesBuilder);
                case "2" -> midPercentage(processesBuilder);
                case "3" -> BTInSegments(processesBuilder);
                case "4" -> nInSegments(processesBuilder);
                case "5" -> densityInSegments(processesBuilder);
                case "6" -> BTLimits(processesBuilder);
                case "7" -> densityLimits(processesBuilder);
                default -> quit = true;
            }
        }
    }

    private static void chooseSegments(ProcessesBuilder processesBuilder) {
        System.out.println("Choose number of segments:");
        int input = userInteger();
        if (processesBuilder.segmentsNumber(input)) {
            System.out.println("Set segments to " + input);
            BTInSegments(processesBuilder);
            nInSegments(processesBuilder);
            densityInSegments(processesBuilder);
        } else {
            System.out.println("Wrong input");
        }
    }

    private static void midPercentage(ProcessesBuilder processesBuilder) {
        System.out.println("Choose medium percentage in [%]:");
        double input = (double) userInteger() / 100.0;
        if (processesBuilder.midPercentage(input)) {
            System.out.println("Set medium Percentage to " + input);
        } else {
            System.out.println("Wrong input");
        }
    }

    private static List<Amount> createAmountList(int n) {
        List<Amount> result = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("Choose amount for " + i + ". segment:");
            System.out.println("""
                    1 - LOW
                    2 - MEDIUM
                    3 - HIGH
                    """);
            String option = sc.nextLine();
            switch (option) {
                case "1" -> result.add(Amount.LOW);
                case default, "2" -> result.add(Amount.MEDIUM);
                case "3" -> result.add(Amount.HIGH);
            }
        }
        return result;
    }

    private static void BTInSegments(ProcessesBuilder processesBuilder) {
        System.out.println("Choose Processes BT in Segments:");
        if (processesBuilder.processesBTinSegments(createAmountList(processesBuilder.getSegments()))) {
            System.out.println("Processes BT set");
        } else {
            System.out.println("Wrong Input");
        }
    }

    private static void nInSegments(ProcessesBuilder processesBuilder) {
        System.out.println("Choose Processes Number in Segments:");
        if (processesBuilder.processesNumberInSegments(createAmountList(processesBuilder.getSegments()))) {
            System.out.println("Processes Number set");
        } else {
            System.out.println("Wrong Input");
        }
    }

    private static void densityInSegments(ProcessesBuilder processesBuilder) {
        System.out.println("Choose Processes Density in Segments:");
        if (processesBuilder.processesDensityInSegments(createAmountList(processesBuilder.getSegments()))) {
            System.out.println("Processes Density set");
        } else {
            System.out.println("Wrong Input");
        }
    }

    private static void BTLimits(ProcessesBuilder processesBuilder) {
        boolean quit = false;
        while (!quit) {
            System.out.println("Choose which Limits to change:");
            System.out.println("""
                    1 - LOW
                    2 - MEDIUM
                    3 - HIGH
                    ANY - Back
                    """);
            String option = sc.nextLine();
            switch (option) {
                case "1" -> BTLimit(Amount.LOW, processesBuilder);
                case "2" -> BTLimit(Amount.MEDIUM, processesBuilder);
                case "3" -> BTLimit(Amount.HIGH, processesBuilder);
                default -> quit = true;
            }
        }
    }

    private static void BTLimit(Amount amount, ProcessesBuilder processesBuilder) {
        System.out.println("Choose min: ");
        int min = userInteger();
        System.out.println("Choose max: ");
        int max = userInteger();
        if (processesBuilder.BTLimits(amount, min, max)) {
            System.out.println("BT " + amount + " limit set to [" + min + ", " + max + "]");
        } else {
            System.out.println("Wrong input");
        }
    }

    private static void densityLimits(ProcessesBuilder processesBuilder) {
        boolean quit = false;
        while (!quit) {
            System.out.println("Choose which Limits to change:");
            System.out.println("""
                    1 - LOW
                    2 - MEDIUM
                    3 - HIGH
                    ANY - Back
                    """);
            String option = sc.nextLine();
            switch (option) {
                case "1" -> densityLimit(Amount.LOW, processesBuilder);
                case "2" -> densityLimit(Amount.MEDIUM, processesBuilder);
                case "3" -> densityLimit(Amount.HIGH, processesBuilder);
                default -> quit = true;
            }
        }
    }

    private static void densityLimit(Amount amount, ProcessesBuilder processesBuilder) {
        System.out.println("Choose min: ");
        int min = userInteger();
        System.out.println("Choose max: ");
        int max = userInteger();
        if (processesBuilder.densityLimits(amount, min, max)) {
            System.out.println("Density " + amount + " limit set to [" + min + ", " + max + "]");
        } else {
            System.out.println("Wrong input");
        }
    }

    private static int userInteger() {
        boolean valid = false;
        int value = Integer.MIN_VALUE;
        while(!valid) {
            try {
                value = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Try again: ");
                sc.nextLine();
            }
            if(value != Integer.MIN_VALUE) {
                valid = true;
            }
        }
        return value;
    }
}
