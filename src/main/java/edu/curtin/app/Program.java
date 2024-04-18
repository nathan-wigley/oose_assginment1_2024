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
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 * ---------
 * Entry point for application which orchestrates all functionality speficied in the spec sheet
 */

 public class Program {
    private static final List<MenuOption> OPTIONS = new ArrayList<>();
    private static TaskManager ts = TaskManager.getInstance();
    private static EstimateEffort estimator;
    public static String fileName = null;
    public static void main(String[] args) throws IOException {
        fileName = getValidFileName(args);
        if (fileName == null) {
            System.out.println("Exiting...");
            return;
        } else if (!ts.loadTasksFromFile(fileName)) {
            System.out.println("Failed to load tasks from file. Exiting...");
            return;
        }
        initializeOptions();
        boolean done = false;
        while (!done) {
            ts.displayWBSAndSummary();
            Menu menu = new Menu(ts);
            int choice = menu.getMenuChoice();
            for (MenuOption option : OPTIONS) {
                if (option.getLabel() == choice) {
                    done = processMenuOption(option);
                    break;
                }
            }
        }
    }

    /*
        The call to begin the process of executing the option selected by the user.
     */

    private static boolean processMenuOption(MenuOption option) {
        option.executeOption(fileName);
        return option instanceof Quit;
    }

    /*
        Checks if the file name provided is valid. If none were provided or if it failed to open/load file,
        it will ask for another filename. To make matters easy, providing the full path to the file works best
     */

    private static String getValidFileName(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName = null;
    
        if (args.length > 0) {
            System.out.println("Attempting to load file: " + args[0]);
            if (new FileIO().checkFileExists(args[0])) {
                return args[0];
            } else {
                System.out.println("The file does not exist. Please enter a valid filename.");
            }    
        }
        while (fileName == null) {
            System.out.println("Please enter the name of an existing file or type 'exit' to quit: ");
            String input = sc.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                return null;
            } else if (new FileIO().checkFileExists(input)) {
                fileName = input;
            } else {
                System.out.println("File does not exist. Please try again.");
            }
        }
        return fileName;
    }

    /*
        Initializing the classes for each option that can be selected within the menu
     */

    private static void initializeOptions() {
        List<Task> tasks = ts.getTasks();
        estimator = new EstimateEffort(tasks);
        OPTIONS.add(estimator);
        OPTIONS.add(new Configure(estimator));
        OPTIONS.add(new Quit());
    }
}