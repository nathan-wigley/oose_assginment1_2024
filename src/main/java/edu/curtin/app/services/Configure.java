package edu.curtin.app.services;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Logger;

import edu.curtin.app.classes.AbstractMenuOption;
import edu.curtin.app.classes.Task;


/**
 * Configuration functionality to allow the user to set the number of estimators and the reconciliation approach
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 */

 public class Configure extends AbstractMenuOption {

    public static int numEstimators;
    public static int reconciliationApproach;

    private static final Logger LOGGER = Logger.getLogger(Configure.class.getName());
    
    public Configure() {
        super(2, "Configure");
    }

    @Override
    public String executeOption(List<Task> taskList, String filename) {
        numEstimators = getNumEstimators();
        reconciliationApproach = getConfigureMenuAndOption();
        System.out.println("Configuration Updated");
        return " ";
    }

    private int getConfigureMenuAndOption() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPLEASE CHOOSE A NEW RECONCILIATION APPROACH\n" + 
        "Select one of the following options\n" + 
        "1. Take the highest estimate\n2. Take the median estimate\n3. Use the estimators revised estimate");
        int num = 3;
        while (true) {
            String input = sc.nextLine();
            try {
                num = Integer.parseInt(input);
                if (num >= 1 && num <= 3) {
                    break;
                } else {
                    System.out.println("Please enter a valid integer between 1 and 3.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("That is not a valid number. Please enter a valid integer.");
            }
        }
        return num;
    }

    private int getNumEstimators(){
        Scanner sc = new Scanner(System.in);
        int result = 0;
        while(true){
            System.out.println("Please enter the new number of estimators: ");
            String num = sc.nextLine();
            try{
                result = Integer.parseInt(num);
                if (result > 0) {
                    break;
                } else {
                    System.out.println("Number of estimators must be a positive integer.");
                }
            }
            catch(NumberFormatException ex){
                System.out.println("That is not a number. Please enter a number.");
            }
        }
        return result;
    }
}
