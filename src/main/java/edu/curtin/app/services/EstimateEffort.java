package edu.curtin.app.services;

import java.io.Console;
import java.util.*;

import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;

public class EstimateEffort implements MenuOption {

    private int estimationApproach = 1; 

    @Override
    public String executeOption(ArrayList<Task> taskList) {
        try {
            Scanner sc = new Scanner(System.in);
            Task task = null;
            while(task == null){
                String taskID = getEffortEstimateID();
                task = findTaskById(taskList, taskID);
            }
            ArrayList<Task> subTasks = getSubTasks(task); 
            if (!subTasks.isEmpty()) {
                for (Task subTask : subTasks) {
                    executeOption(taskList);
                }
            } else {
                estimateEffortForTask(task);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return "Effort estimation completed.";
    }

    private String getEffortEstimateID() {
            System.out.println("Please enter a taskID");
            Scanner sc = new Scanner(System.in);
            String taskID = null;
            try{
                taskID = String.valueOf(sc.nextLine());
            }catch(InputMismatchException ex){
                System.out.println("Not a string: " + ex);
            }
            return taskID;
    }

    @Override
    public int getLabel() {
        return 1;
    }

    private Task findTaskById(ArrayList<Task> taskList, String taskID) {

        while(true){
            for (Task task : taskList) {
                if (task.getTaskID().equals(taskID)) {
                    return task;
                }
                else{
                    System.out.println("That task ID does not exist in the data provided");
                    return null;
                }
            }
        }
    }

    private ArrayList<Task> getSubTasks(Task task) {
        // needs to be written
        return new ArrayList<>();
    }


    private void estimateEffortForTask(Task task) {
        ArrayList<Integer> estimates = new ArrayList<>();
        Console cnsl = System.console();
        System.out.println("Estimating effort for task: " + task.getTaskID());
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter estimate #" + (i + 1) + ": ");
            char[] input = cnsl.readPassword();
            // estimates.add(Integer.parseInt(input));
        }
        System.out.println("Estimates: " + estimates);
        reconcileEstimates(estimates, task);
    }

    private void reconcileEstimates(ArrayList<Integer> estimates, Task task) {
        Collections.sort(estimates);
        int result = 0;
        switch (estimationApproach) {
            case 1:
                result = estimates.get(estimates.size() - 1);
                break;
            case 2:
                result = estimates.get(estimates.size() / 2);
                break;
        }
        task.setEffortEstimate(result);
        System.out.println("Reconciled effort for task " + task.getTaskID() + ": " + result);
    }
    
}
