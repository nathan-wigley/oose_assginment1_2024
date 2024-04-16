package edu.curtin.app.classes;

import java.util.List;
import java.util.Scanner;

import edu.curtin.app.interfaces.EstimationStrategy;

public class RevisedEstimation implements EstimationStrategy{

    @Override
    public int estimate(List<Integer> estimates) {
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        System.out.println("Please enter the newly revised estimate: ");
        while (!done) {
            try {
                int newEstimate = Integer.parseInt(sc.nextLine());
                done = true;
                return newEstimate;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
        return -1;
    }
    
}
