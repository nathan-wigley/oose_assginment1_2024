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
    private static TaskManager ts = new TaskManager();
    public static String fileName = null;
    public static void main(String[] args) throws IOException {
        fileName = getValidFileName(args);
        if (fileName == null || !ts.loadTasksFromFile(fileName)) {
            System.out.println("No valid file provided or failed to load tasks. Exiting...");
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

    private static boolean processMenuOption(MenuOption option) {
        option.executeOption(ts.getTasks(), fileName);
        return option instanceof Quit;
    }

    private static String getValidFileName(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (args.length > 0) {
            System.out.println("Attempting to load file: " + args[0]);
            return args[0];
        } else {
            System.out.println("Please enter the name of an existing file: ");
            return sc.nextLine();
        }
    }

    private static void initializeOptions() {
        OPTIONS.add(new EstimateEffort());
        OPTIONS.add(new Configure());
        OPTIONS.add(new Quit());
    }
}