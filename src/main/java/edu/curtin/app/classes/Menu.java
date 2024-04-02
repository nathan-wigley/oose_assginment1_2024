package edu.curtin.app.classes;

import java.util.*;

public class Menu {
    
    public void getMenuDisplay(){
        System.out.println("\nWELCOME TO THE WORK PLANNING AND EFFORT ESTIMATION PROGRAM\n" + 
        "Please select one of the following options\n" + 
        "1. Estimate Effort\n2. Configure\n3. Quit");
    }

    public int getMenuChoice() {
        Scanner sc = new Scanner(System.in);
        getMenuDisplay();
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
            return -1;
        }
    }
}
