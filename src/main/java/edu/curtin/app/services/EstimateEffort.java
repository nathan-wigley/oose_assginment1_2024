package edu.curtin.app.services;

import java.io.Console;
import java.util.*;

import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;

public class EstimateEffort implements MenuOption {

    private int estimationApproach = 1; 

    @Override
    public String executeOption(List<Task> taskList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a taskID:");
        String taskID = sc.nextLine();
        Task task = findTaskById(taskList, taskID);

        if (task != null) {
            estimateEffortForTaskAndSubtasks(task, taskList);
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
        System.out.println("That task ID does not exist in the data provided.");
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
        List<Integer> estimates = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int estimatesCount = 0;
        
        System.out.println("Estimating effort for task: " + task.getTaskID());
        while (estimatesCount < 3) {
            System.out.print("Enter estimate " + (estimatesCount + 1) + ": ");
            try {
                int estimate = Integer.parseInt(sc.nextLine());
                estimates.add(estimate);
                estimatesCount++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
        System.out.println("Estimates: " + estimates);
        reconcileEstimates(estimates, task);
    }

    private void reconcileEstimates(List<Integer> estimates, Task task) {
        Collections.sort(estimates);
        int result = 0;
        switch (estimationApproach) {
            case 1:
                result = estimates.get(estimates.size() - 1);
                break;
            case 2:
                result = estimates.get(estimates.size() / 2);
                break;
            default:
                System.out.println("Unexpected estimation approach selected. Defaulting to median estimate.");
                result = estimates.get(estimates.size() / 2);
                break;
                
        }
        task.setEffortEstimate(result);
        System.out.println("Reconciled effort for task " + task.getTaskID() + ": " + result);
    }

    private void estimateEffortForTaskAndSubtasks(Task task, List<Task> allTasks) {
        List<Task> subTasks = getSubTasksForParent(task, allTasks);
        if (subTasks.isEmpty() == false) {
            for (Task subTask : subTasks) {
                estimateEffortForTaskAndSubtasks(subTask, allTasks);
            }
        } else {
            estimateEffortForTask(task);
        }
    }
    
}
