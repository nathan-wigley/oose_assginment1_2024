package edu.curtin.app.interfaces;

/*
    Interface used to define methods held by multiple classes that do different things,
    but can be executed in the same context as eachother eg. menu selection
 */

public interface MenuOption {
    
    String executeOption(String filename);
    
    int getLabel();
}