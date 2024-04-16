package edu.curtin.app.services;
import java.util.*;

import edu.curtin.app.classes.RevisedEstimation;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.EstimationStrategy;
import edu.curtin.app.interfaces.MenuOption;


public class EstimateEffort implements MenuOption {
    private EstimationStrategy estimationStrategy;
    private List<Task> taskList;

    public EstimateEffort(List<Task> taskList) {
        this.taskList = taskList;
        this.estimationStrategy = new RevisedEstimation();
    }

    public void setEstimationStrategy(EstimationStrategy strategy) {
        this.estimationStrategy = strategy;
    }

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

    @Override
    public int getLabel() {
        return 1;
    }

    private Task findTaskById(List<Task> taskList, String taskID) {
        for (Task task : taskList) {
            if (task.getTaskID().equals(taskID)) {
                return task;
            }
        }
        System.out.println("That task ID does not exist");
        return null;
    }

    private List<Task> getSubTasksForParent(Task parentTask, List<Task> allTasks) {
        List<Task> subTasks = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getParentID() != null && task.getParentID().equals(parentTask.getTaskID())) {
                subTasks.add(task);
            }
        }
        return subTasks;
    }
    
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

    private List<Integer> collectEstimates() {
        List<Integer> estimates = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Collecting estimates now Enter estimates:");
        int numEstimators = Configure.numEstimators <= 0 ? 3 : Configure.numEstimators;
        for (int i = 0; i < numEstimators; i++) {
            System.out.print("Enter estimate " + (i + 1) + ": ");
            try {
                int estimate = Integer.parseInt(sc.nextLine());
                estimates.add(estimate);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
                i--;
            }
        }
        return estimates;
    }
}
