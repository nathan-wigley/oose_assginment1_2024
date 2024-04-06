package edu.curtin.app.services;

import java.util.List;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;

public class Quit implements MenuOption{

    @Override
    public String executeOption(List<Task> taskList) {
        return "Quitting...";
    }

    @Override
    public int getLabel() {
        return 3;
    }
    
}
