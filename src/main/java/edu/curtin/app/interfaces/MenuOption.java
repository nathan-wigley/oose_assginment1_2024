package edu.curtin.app.interfaces;

import java.util.*;
import edu.curtin.app.classes.Task;

public interface MenuOption {
    
    String executeOption(ArrayList<Task> taskLis);
    
    int getLabel();
}