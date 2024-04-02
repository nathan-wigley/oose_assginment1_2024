package edu.curtin.app.classes;

import edu.curtin.app.interfaces.MenuOption;

public abstract class AbstractMenuOption implements MenuOption {
    private int label;
    private String description;

    public AbstractMenuOption(int label, String description) {
        this.label = label;
        this.description = description;
    }

    @Override
    public int getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    // Keep executeOption(List<Task> taskList) as abstract to be implemented by subclasses
}