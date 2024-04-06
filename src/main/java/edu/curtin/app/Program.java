package edu.curtin.app;

import java.io.IOException;
import java.util.*;
import edu.curtin.app.classes.Menu;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;
import edu.curtin.app.services.Configure;
import edu.curtin.app.services.EstimateEffort;
import edu.curtin.app.services.FileIO;
import edu.curtin.app.services.Quit;
import edu.curtin.app.services.TaskManager;

/**
 * Entry point for application
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 */

 public class Program {
    private static List<Task> taskList = new ArrayList<>();
    private static final List<MenuOption> OPTIONS = new ArrayList<>();
    private static TaskManager ts= new TaskManager();

    public static void main(String[] args) throws IOException {
        initializeOptions();
        FileIO io = new FileIO();
        Menu menu = new Menu(ts);
        Scanner sc = new Scanner(System.in);
        String fileName = getValidFileName(args, sc, io);
        if (fileName != null) {
            taskList = io.loadList(fileName);
        } else {
            System.out.println("No valid file provided. Exiting...");
            return;
        }
    
        boolean done = false;
        while (!done) {
            
            int choice = menu.getMenuChoice();
            for (MenuOption option : OPTIONS) {
                if (option.getLabel() == choice) {
                    option.executeOption(taskList);
                    if (option instanceof Quit) {
                        done = true;
                    }
                    break;
                }
            }
        }
    }
    private static void initializeOptions() {
        OPTIONS.add(new EstimateEffort());
        OPTIONS.add(new Configure());
        OPTIONS.add(new Quit());
    }

    private static String getValidFileName(String[] args, Scanner sc, FileIO io) {
        if (args.length > 0 && io.checkFileExists(args[0])) {
            return args[0];
        }
        System.out.println("Please enter the name of an existing file: ");
        while (true) {
            String input = sc.nextLine();
            if (io.checkFileExists(input)) {
                return input;
            } else if (input.equalsIgnoreCase("exit")) {
                return null;
            }
            System.out.println("File does not exist. Please try again or type 'exit' to quit: ");
        }
    }
}