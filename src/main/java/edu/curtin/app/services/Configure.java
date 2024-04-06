package edu.curtin.app.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.curtin.app.classes.AbstractMenuOption;
import edu.curtin.app.classes.Estimates;
import edu.curtin.app.classes.Task;

public class Configure extends AbstractMenuOption {

    private static final Logger LOGGER = Logger.getLogger(Configure.class.getName());

    public Configure() {
        super(2, "Configure");
    }

    @Override
    public String executeOption(List<Task> taskList) {
        try {
            int selectedOption = getConfigureMenuAndOption();
            Estimates estimates = getMaxAndMedianEstimates(taskList);
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "Selected option: " + selectedOption + ". Max effort: " + estimates.getMaxEstimate() + ", Median effort: " + estimates.getMedianEstimate());
            }
        } catch (InputMismatchException ex) {
            LOGGER.log(Level.SEVERE, "Input mismatch while selecting the configuration option.", ex);
        }
        return " ";
    }

    public int getConfigureMenuAndOption() {
        while (true) {
            System.out.println("\nPLEASE CHOOSE A NEW RECONCILIATION APPROACH\n" + 
            "Select one of the following options\n" + 
            "1. Take the highest estimate\n2. Take the median estimate\n3. Use the estimators revised estimate");
            try {
                Scanner sc = new Scanner(System.in);
                int num = sc.nextInt();
                if (num >= 1 && num <= 3) {
                    return num;
                } else {
                    LOGGER.log(Level.WARNING, "Please enter a valid integer");
                }
            } catch (InputMismatchException ex) {
                LOGGER.log(Level.SEVERE, "That is not an integer.", ex);
            }
        }
    }

    private Estimates getMaxAndMedianEstimates(List<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("Task list is empty.");
            return new Estimates(0, 0);
        }
        List<Task> sortedList = new ArrayList<>(taskList);
        sortedList.sort(Comparator.comparingInt(Task::getEffortEstimate));
        int maxEffort = sortedList.get(sortedList.size() - 1).getEffortEstimate();

        double medianEffort;
        int size = sortedList.size();
        if (size % 2 == 0) {
            medianEffort = (sortedList.get(size / 2 - 1).getEffortEstimate() + 
                            sortedList.get(size / 2).getEffortEstimate()) / 2.0;
        } else {
            medianEffort = sortedList.get(size / 2).getEffortEstimate();
        }

        return new Estimates(maxEffort, medianEffort);
    }
}
