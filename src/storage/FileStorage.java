package src.storage;

import src.model.BasicTask;
import src.model.LimitedTimeTask;
import src.model.RepeatableTask;
import src.model.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileStorage {
    private static final String FILE_PATH = "src/tasks.txt";

    public static void saveTasks(Collection<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.println(task.serialize());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Map<String, Task> loadTasks() {
        Map<String, Task> tasks = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens[0].equals("BASIC")) {
                    tasks.put(tokens[1], new BasicTask(tokens[1], tokens[2], tokens[3]));
                } else if (tokens[0].equals("REPEATABLE")) {
                    tasks.put(tokens[1], new RepeatableTask(tokens[1], tokens[2], tokens[3]
                            , Integer.parseInt(tokens[4]), LocalDateTime.parse(tokens[5])));
                }else {
                    tasks.put(tokens[1], new LimitedTimeTask(tokens[1], tokens[2], tokens[3], LocalDateTime.parse(tokens[4])));
                }
            }
        } catch (IOException e) {

        }
        return tasks;
    }
}
