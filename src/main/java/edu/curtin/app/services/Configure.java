package edu.curtin.app.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import edu.curtin.app.classes.AbstractMenuOption;
import edu.curtin.app.classes.Task;

public class Configure extends AbstractMenuOption {

    public Configure() {
        super(2, "Configure");
    }

    @Override
    public String executeOption(ArrayList<Task> taskList) {
        
        try{
            getConfigureMenu();
            getMaxAndMedianEstimates(taskList);

        }catch(InputMismatchException ex){
            ex.printStackTrace();
        }
        return " ";
    }

    public int getConfigureMenu(){
        while (true) {
            System.out.println("\nPLEASE CHOOSE A NEW RECONCILIATION APPROACH\n" + 
            "Select one of the following options\n" + 
            "1. Take the highest estimate\n2. Take the median estimate\n3. Use the estimators revised estimate");
            try{
                Scanner sc = new Scanner(System.in);
                int num = sc.nextInt();
                if(num>=1 && num <=3){
                    return num;
                }
                else{
                    System.out.println("Please enter a valid integer ");
                }
            }
            catch(InputMismatchException ex){
                System.out.println("That is not an integer: " + ex);
            }
            catch(NumberFormatException nex){
                nex.printStackTrace();
            }
        }
    }

    private void getMaxAndMedianEstimates(ArrayList<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("Task list is empty.");
            return;
        }
        ArrayList<Task> sortedList = new ArrayList<>(taskList);
        sortedList.sort(Comparator.comparingInt(Task::getEffortEstimate));
        int maxEffort = sortedList.get(sortedList.size() - 1).getEffortEstimate();
        System.out.println("Maximum effort estimate: " + maxEffort);
        double medianEffort;
        int size = sortedList.size();
        if (size % 2 == 0) {
            medianEffort = ((double)sortedList.get(size / 2 - 1).getEffortEstimate() + 
                            (double)sortedList.get(size / 2).getEffortEstimate()) / 2;
        } else {
            medianEffort = (double)sortedList.get(size / 2).getEffortEstimate();
        }
        System.out.println("Median effort estimate: " + medianEffort);
    }
}
