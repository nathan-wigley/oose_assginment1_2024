package edu.curtin.app.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import edu.curtin.app.classes.Task;

public class FileIO {

    private static final Logger LOGGER = Logger.getLogger(FileIO.class.getName());

    public FileIO() {
    }

    public boolean checkFileExists(String filename) {
        return Files.exists(Paths.get(filename));
    }

    public List<Task> loadList(String filename) throws IOException {
        List<Task> taskList = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                if (parts.length >= 3) {
                    Task newTask = parseLineToTask(parts);
                    taskList.add(newTask);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, "Failed to load task list from file: " + filename, e);
            }
        }
        return taskList;
    }

    private Task parseLineToTask(String[] parts) {
        String parentID = parts[0].isEmpty() ? null : parts[0];
        String taskID = parts[1];
        String taskDesc = parts[2];
        int effortEstimate = parts.length > 3 && !parts[3].isEmpty() ? Integer.parseInt(parts[3]) : 0;
        return new Task(parentID, taskID, taskDesc, effortEstimate);
    }

    public void writeTasksToFile(String filename, List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                String line = constructLineFromTask(task);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write task list to file: " + filename, e);
            throw e;
        }
    }

    private String constructLineFromTask(Task task) {
        String parentPart = (task.getParentID() != null && !task.getParentID().isEmpty()) ? task.getParentID() + " ; " : "; ";
        String taskIDAndDesc = task.getTaskID() + " ; " + task.getTaskDesc();
        String effortPart = task.getEffortEstimate() > 0 ? " ; " + task.getEffortEstimate() : ";";
        return parentPart + taskIDAndDesc + effortPart;
    }
}