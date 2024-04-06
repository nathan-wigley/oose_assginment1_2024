package edu.curtin.app.classes;

import java.util.*;

public class Menu {
    
    public String getMenuDisplay(){
        String menu = "\nWELCOME TO THE WORK PLANNING AND EFFORT ESTIMATION PROGRAM\n" + 
        "Please select one of the following options\n" + 
        "1. Estimate Effort\n2. Configure\n3. Quit";
        return menu;
    }

    public int getMenuChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println(getMenuDisplay());
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
            return -1;
        }
    }
}
