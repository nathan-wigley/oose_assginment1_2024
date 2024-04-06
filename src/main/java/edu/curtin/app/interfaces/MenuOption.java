package edu.curtin.app.interfaces;

import java.util.*;
import edu.curtin.app.classes.Task;

public interface MenuOption {
    
    String executeOption(List<Task> taskLis);
    
    int getLabel();
}