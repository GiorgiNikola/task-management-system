package src.service;

import src.model.BasicTask;
import src.model.LimitedTimeTask;
import src.model.RepeatableTask;
import src.model.Task;
import src.storage.FileStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskService {
    private Map<String, Task> taskMap;

    public TaskService() {
        this.taskMap = FileStorage.loadTasks();
    }

    public boolean createBasicTask(String taskName, String description, String creatorName) {
        return addTask(new BasicTask(taskName, description, creatorName));
    }

    public boolean createLimitedTask(String taskName, String description, String creatorName, LocalDateTime deadline) {
        return addTask(new LimitedTimeTask(taskName, description, creatorName, deadline));
    }

    public boolean createRepeatableTask(String name, String desc, String creator, int repeatCount, LocalDateTime date) {
        return addTask(new RepeatableTask(name, desc, creator, repeatCount, date));
    }

    public boolean addTask(Task task) {
        if (taskMap.containsKey(task.getTaskName()))
            return false;
        taskMap.put(task.getTaskName(), task);
        update();
        return true;
    }

    public List<String> getAllTaskNames() {
        return new ArrayList<>(taskMap.keySet());
    }

    public Task getTask(String taskName) {
        return taskMap.get(taskName);
    }

    public boolean deleteTask(String name) {
        boolean removed = taskMap.remove(name) != null;
        if (removed) update();
        return removed;
    }

    public boolean updateTask(String taskName, String newDescription, LocalDateTime newDeadline,
                              Integer newRepeatCount, LocalDateTime newRepeatDate) {
        Task task = getTask(taskName);
        if (task == null) {
            return false;
        }
        task.setDescription(newDescription);
        if (task instanceof LimitedTimeTask && newDeadline != null) {
            ((LimitedTimeTask) task).setDeadline(newDeadline);
        }

        if (task instanceof RepeatableTask && newRepeatCount != null && newRepeatDate != null) {
            ((RepeatableTask) task).setRepeatCount(newRepeatCount);
            ((RepeatableTask) task).setRepeatDate(newRepeatDate);
        }
        update();
        return true;
    }

    public void update() {
        FileStorage.saveTasks(taskMap.values());
    }
}
