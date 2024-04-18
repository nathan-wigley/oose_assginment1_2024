package edu.curtin.app.services;
import java.util.*;

import edu.curtin.app.classes.RevisedEstimation;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.EstimationStrategy;
import edu.curtin.app.interfaces.MenuOption;

/**
 * EstimateEffort.java : Class
 * @author Nathan Wigley (20644750)
 --------
  This class implements all methods outlined in the MenuOption Interface.
  It also handles all functionality for estimators to accurately estimate a tasks effort via an integer value.
  This class looks at the config too to make sure that the correct number of estimators and the correct
  estimation strategy are used.
 */

public class EstimateEffort implements MenuOption {
    private EstimationStrategy estimationStrategy;
    private List<Task> taskList;

    /*
        Constructor for the EstimateEffort class
     */

    public EstimateEffort(List<Task> taskList) {
        this.taskList = taskList;
        this.estimationStrategy = new RevisedEstimation(); //default if nothing is configured prior
    }

    /*
        Modifier for the estimation strategy.
     */

    public void setEstimationStrategy(EstimationStrategy strategy) {
        this.estimationStrategy = strategy;
    }

    /*
        This is the main method which is executed as part of the interface functionality and
        its polymorphism capability. Essentially this method will orchestrate this whole class
        to provide the functionality of Effort Estimation correctly.
     */

    @Override
    public String executeOption(String filename) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a taskID:");
        String taskID = sc.nextLine();
        Task task = findTaskById(taskList, taskID);

        if (task != null) {
            List<Task> subTasks = getSubTasksForParent(task, taskList);
            if (subTasks.isEmpty()) {
                estimateEffortForTask(task);
            } else {
                System.out.println("\nParent task found. Estimating effort of sub-tasks\n");
                for (Task subTask : subTasks) {
                    estimateEffortForTask(subTask);
                }
            }
        } else {
            System.out.println("Task not found. Please try again.");
        }
        return "Effort estimation completed.";
    }

    /*
        Interface method to provide the integer for the main menu option: Estimate Effort.
     */
    @Override
    public int getLabel() {
        return 1;
    }

    /*
        This is a searching function which will look through the list of tasks and find a task 
        based on the ID provided to the function.
     */

    private Task findTaskById(List<Task> taskList, String taskID) {
        for (Task task : taskList) {
            if (task.getTaskID().equals(taskID)) {
                return task;
            }
        }
        System.out.println("That task ID does not exist");
        return null;
    }

    /*
        This method will loop through each task and get all the subtasks (if any)
     */

    private List<Task> getSubTasksForParent(Task parentTask, List<Task> allTasks) {
        List<Task> subTasks = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getParentID() != null && task.getParentID().equals(parentTask.getTaskID())) {
                subTasks.add(task);
            }
        }
        return subTasks;
    }

    /*
        Checks the validity of the task being able to be estimated. If it is a parent task, then it
        cannot be estimated, but instead all its sub tasks will be estimated instead.
     */
    
    private void estimateEffortForTask(Task task) {
        if (getSubTasksForParent(task, taskList).isEmpty()) {
            System.out.println("Estimating Task " + task.getTaskID());
            List<Integer> estimates = collectEstimates();
            int result = estimationStrategy.estimate(estimates);
            task.setEffortEstimate(result);
            System.out.println("Effort for " + task.getTaskID() + " set to: " + result);
        } else {
            System.out.println("Task " + task.getTaskID() + " has subtasks which will be estimated");
            for (Task subTask : getSubTasksForParent(task, taskList)) {
                estimateEffortForTask(subTask);
            }
        }
    }


    /*
        Method the collect the integer estimates from each of the estimators.
        Called for each task
     */

    private List<Integer> collectEstimates() {
        List<Integer> estimates = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Collecting estimates now. Enter estimates:");
        int numEstimators = Configure.numEstimators <= 0 ? 3 : Configure.numEstimators;
        int i = 0;
        while (i < numEstimators) {
            System.out.print("Enter estimate " + (i + 1) + ": ");
            try {
                int estimate = Integer.parseInt(sc.nextLine());
                estimates.add(estimate);
                i++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
        return estimates;
    }
}
