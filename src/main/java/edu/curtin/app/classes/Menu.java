package edu.curtin.app.classes;

import java.util.*;
import edu.curtin.app.services.TaskManager;

/**
  @author Nathan Wigley 20644750
  -------
  This class handles the Menu's design and also the choice that is selected by the user once
  the menu has been displayed
 */

public class Menu {
    public TaskManager taskManager;

    public Menu(TaskManager tm) {
        taskManager = tm;
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

    public String getMenuDisplay(){
        String menu = "\nWELCOME TO THE WORK PLANNING AND EFFORT ESTIMATION PROGRAM\n" +
        "Please select one of the following options\n" +
        "1. Estimate Effort\n2. Configure\n3. Quit";
        return menu;
    }
}

