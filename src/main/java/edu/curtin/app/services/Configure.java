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
    public String executeOption(List<Task> taskList) {
        reconciliationApproach = getConfigureMenuAndOption();
        numEstimators = getNumEstimators();

        return " ";
    }

    public int getConfigureMenuAndOption() {
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        int num = 3;
        while (!done) {
            numEstimators = getNumEstimators();
            System.out.println("\nPLEASE CHOOSE A NEW RECONCILIATION APPROACH\n" + 
            "Select one of the following options\n" + 
            "1. Take the highest estimate\n2. Take the median estimate\n3. Use the estimators revised estimate");
            try {
                num = sc.nextInt();
                if (num >= 1 && num <= 3) {
                    done = true;
                    return num;
                } else {
                    LOGGER.log(Level.WARNING, "Please enter a valid integer");
                }
            } catch (InputMismatchException ex) {
                LOGGER.log(Level.SEVERE, "That is not an integer.", ex);
            }
        }
        return num;
    }

    private int getNumEstimators(){
        Scanner sc = new Scanner(System.in);
        int result = 0;
        while(result==0){
            System.out.println("Please enter the new number of estimators ");
            String num = sc.nextLine();
            try{
                result = Integer.parseInt(num);
                return result;
            }
            catch(InputMismatchException ex){
                System.out.println("That is not a number. Enter a number");
            }
        }
        return result;
    }
}