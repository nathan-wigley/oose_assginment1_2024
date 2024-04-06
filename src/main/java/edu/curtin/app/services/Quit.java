package edu.curtin.app.services;

import java.io.IOException;
import java.util.List;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;

public class Quit implements MenuOption{

    @Override
    public String executeOption(List<Task> taskList, String filename) {
        try {
            new FileIO().writeTasksToFile(filename, taskList);
            System.out.println("WBS changes have been saved.");
        } catch (IOException e) {
            System.err.println("Error saving WBS changes: " + e.getMessage());
        }
        return "Quitting";
    }

    @Override
    public int getLabel() {
        return 3;
    }
}
