package edu.curtin.app.services;
import java.util.Scanner;

import edu.curtin.app.classes.AbstractMenuOption;
import edu.curtin.app.classes.MaxEstimation;
import edu.curtin.app.classes.MedianEstimation;
import edu.curtin.app.classes.RevisedEstimation;


/**
 * Configuration functionality to allow the user to set the number of estimators and the reconciliation approach
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 */

 public class Configure extends AbstractMenuOption {

    public static int numEstimators = 3; //default is 3
    public static int reconciliationApproach;
    private EstimateEffort estimator;

    public Configure(EstimateEffort estimator) {
        super(2, "Configure");
        this.estimator = estimator;
    }

    @Override
    public String executeOption(String filename) {
        numEstimators = getNumEstimators();
        reconciliationApproach = getConfigureMenuAndOption();
        applyReconciliationStrategy();
        System.out.println("Configuration Updated");
        return "Configuration updated with " + numEstimators + " estimators and approach " + reconciliationApproach;
    }

    private void applyReconciliationStrategy() {
        switch (reconciliationApproach) {
            case 1:
                estimator.setEstimationStrategy(new MaxEstimation());
                break;
            case 2:
                estimator.setEstimationStrategy(new MedianEstimation());
                break;
            case 3:
                estimator.setEstimationStrategy(new RevisedEstimation());
                break;
            default:
                System.out.println("Invalid option, setting to maximum by default.");
                estimator.setEstimationStrategy(new MaxEstimation());
                break;
        }
    }

    private int getConfigureMenuAndOption() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPlease choose a new reconciliation approach:\n" +
                           "1. Take the highest estimate\n" +
                           "2. Take the median estimate\n" +
                           "3. Let the estimating panel decide");
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice > 0 && choice <=3) {
                    return choice;
                } else {
                    System.out.println("Please enter a valid option: 1, 2 or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
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
