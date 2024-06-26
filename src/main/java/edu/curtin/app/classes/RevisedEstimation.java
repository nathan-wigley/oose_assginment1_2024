package edu.curtin.app.classes;

import java.util.List;
import java.util.Scanner;

import edu.curtin.app.interfaces.EstimationStrategy;

public class RevisedEstimation implements EstimationStrategy{

/*
  When selected as the Estimation Approach, it will ask the estimators to discuss the estimations
  and come up with a value that best judges the estimation for that particular task.
  This is a part of the Strategy Pattern
 */

    @Override
    public int estimate(List<Integer> estimates) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the newly revised estimate: ");
        while (true) {
            try {
                int newEstimate = Integer.parseInt(sc.nextLine());
                return newEstimate;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
    }
    
}
