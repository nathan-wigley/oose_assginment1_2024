package edu.curtin.app.services;

import edu.curtin.app.classes.Task;

import java.io.IOException;
import java.util.*;
// disclaimer, not anything to do with windows task manager, just a cool name :)
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
}
