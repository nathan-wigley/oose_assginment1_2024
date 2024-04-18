package edu.curtin.app.classes;

/*
    This class represents a given task in the WBS, which is practically just a line.
 */

public class Task {
    private String parentID; //can be empty, depends on the task
    private String taskID; // id of the current task
    private String taskDesc; // may contain  any characters   except  \n  and   ;
    private int effortEstimate; //a number classifying how long a task will take 

    //CONSTRUCTOR
    
    public Task(String parentID, String taskID, String taskDesc, int effortEstimate){
        this.parentID = parentID;
        this.taskID = taskID;
        this.taskDesc = taskDesc;
        this.effortEstimate = effortEstimate;
    }

    //ACCESSORS AND MUTATORS

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public int getEffortEstimate() {
        return effortEstimate;
    }

    public void setEffortEstimate(int effortEstimate) {
        this.effortEstimate = effortEstimate;
    }
}