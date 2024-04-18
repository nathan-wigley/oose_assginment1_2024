package edu.curtin.app.services;

import edu.curtin.app.classes.Task;

import java.io.IOException;
import java.util.*;

/**
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 * ------
 * Task manager to load tasks into a list
 */

 public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private final FileIO fileIO = new FileIO();
    private static TaskManager ts = new TaskManager();

    private TaskManager() {}

    public static TaskManager getInstance() {
        return ts;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean loadTasksFromFile(String fileName) throws IOException {
        if (fileIO.checkFileExists(fileName)) {
            this.tasks = fileIO.loadList(fileName);
            return true;
        }
        return false;
    }

    public void displayWBSAndSummary() {
        System.out.println("\nCURRENT WORK BREAKDOWN STRUCTURE\n");
        printWBS("", null);
        printSummary();
    }

    private void printWBS(String indent, String parentID) {
        tasks.stream()
             .filter(task -> Objects.equals(task.getParentID(), parentID))
             .forEach(task -> {
                 System.out.println(indent + task.getTaskID() + ": " + task.getTaskDesc() 
                                                              + (task.getEffortEstimate() > 0 ? " ; " 
                                                              + task.getEffortEstimate() : ""));
                 printWBS(indent + "  ", task.getTaskID());
             });
    }

    private void printSummary() {
        int totalEffort = 0;
        long unknownTasks = 0;
    
        for (Task task : tasks) {
            if (task.getEffortEstimate() > 0) {
                totalEffort += task.getEffortEstimate();
            }
            if (task.getEffortEstimate() == 0 && task.getParentID() != null) {
                unknownTasks++;
            }
        }
        System.out.println("\nTotal of all known estimates = " + totalEffort);
        System.out.println("Number of unknown tasks = " + unknownTasks);
    }
}

