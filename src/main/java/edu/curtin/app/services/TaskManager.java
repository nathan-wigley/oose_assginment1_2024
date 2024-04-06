package edu.curtin.app.services;

import edu.curtin.app.classes.Task;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
// disclaimer, not anything to do with windows task manager :/
/**
 * Task manager to load tasks into a list
 * created at: 25/03/24
 * @author Nathan Wigley (20644750)
 */

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private final FileIO fileIO = new FileIO();

    public boolean loadTasksFromFile(String fileName) throws IOException {
        if (fileIO.checkFileExists(fileName)) {
            tasks = fileIO.loadList(fileName);
            return true;
        }
        return false;
    }

    public void displayWBSAndSummary() {
        displayWBS(tasks, "", new HashSet<>());
    }

    private void displayWBS(List<Task> tasks, String indent, Set<String> displayed) {
        for (Task task : tasks) {
            if (!displayed.contains(task.getTaskID())) {
                System.out.println(indent + task.getTaskID() + ": " + task.getTaskDesc() 
                                                             + (task.getEffortEstimate() > 0 ? ", effort = " 
                                                             + task.getEffortEstimate() : ""));
                displayed.add(task.getTaskID());
                List<Task> subtasks = tasks.stream()
                                           .filter(t -> task.getTaskID().equals(t.getParentID()))
                                           .collect(Collectors.toList());
                displayWBS(subtasks, indent + "  ", displayed);
            }
        }
    }
}
