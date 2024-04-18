package edu.curtin.app.classes;

import edu.curtin.app.interfaces.MenuOption;

public abstract class AbstractMenuOption implements MenuOption {
    private int label;

    public AbstractMenuOption(int label) {
        this.label = label;
    }

    @Override
    public int getLabel() {
        return label;
    }
}