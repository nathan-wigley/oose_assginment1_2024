package edu.curtin.app.services;

import java.io.Console;
import java.util.*;

import edu.curtin.app.classes.Estimates;
import edu.curtin.app.classes.Task;
import edu.curtin.app.interfaces.MenuOption;


public class EstimateEffort implements MenuOption {

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
        int numEstimators = 3;
        if(Configure.numEstimators <= 0){
            numEstimators = 3;
        }
        else{
            numEstimators = Configure.numEstimators;
        }
        
        System.out.println("Estimating effort for task: " + task.getTaskID() + " with " + numEstimators + " estimators");
        while (estimatesCount < numEstimators) {
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
        int estimationApproach = Configure.reconciliationApproach;
        if(Configure.reconciliationApproach <= 0){
            estimationApproach = 3;
        }
        switch (estimationApproach) {
            case 1:
                System.out.println("Maximum Approach");
                result = estimates.get(estimates.size() - 1);
                break;
            case 2:
                System.out.println("Median approach");
                result = estimates.get(estimates.size() / 2);
                break;
            case 3:
                System.out.println("Let the estimators dicuss and decide");
                result = estimates.get(estimates.size() / 2);
                break;
            default:
                System.out.println("Unexpected estimation approach selected. Defaulting to discussion by estimators.");
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

    private Estimates getMaxAndMedianEstimates(List<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("Task list is empty.");
            return new Estimates(0, 0);
        }
        List<Task> sortedList = new ArrayList<>(taskList);
        sortedList.sort(Comparator.comparingInt(Task::getEffortEstimate));
        int maxEffort = sortedList.get(sortedList.size() - 1).getEffortEstimate();

        double medianEffort;
        int size = sortedList.size();
        if (size % 2 == 0) {
            medianEffort = (sortedList.get(size / 2 - 1).getEffortEstimate() + 
                            sortedList.get(size / 2).getEffortEstimate()) / 2.0;
        } else {
            medianEffort = sortedList.get(size / 2).getEffortEstimate();
        }

        return new Estimates(maxEffort, medianEffort);
    }
    
}
