package src.storage;


import src.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskStorage {
    private Map<String, Task> taskMap = new HashMap<>();

    public boolean addTask(Task task) {
        if (taskMap.containsKey(task.getTaskName()))
            return false;
        taskMap.put(task.getTaskName(), task);
        return true;
    }

    public Task getTask(String taskName) {
        return taskMap.get(taskName);
    }

    public boolean removeTask(String taskName) {
        return taskMap.remove(taskName) != null;
    }

    public List<String> getAllTaskNames() {
        return new ArrayList<>(taskMap.keySet());
    }

}
