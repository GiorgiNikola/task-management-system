package src.service;

import src.model.BasicTask;
import src.model.LimitedTimeTask;
import src.model.RepeatableTask;
import src.model.Task;
import src.storage.TaskStorage;

import java.time.LocalDateTime;

public class TaskService {
    private final TaskStorage storage;

    public TaskService(TaskStorage storage) {
        this.storage = storage;
    }

    public boolean createBasicTask(String taskName, String description, String creatorName) {
        return storage.addTask(new BasicTask(taskName, description, creatorName));
    }

    public boolean createLimitedTask(String taskName, String description, String creatorName, LocalDateTime deadline) {
        return storage.addTask(new LimitedTimeTask(taskName, description, creatorName, deadline));
    }

    public boolean createRepeatableTask(String name, String desc, String creator, int repeatCount, LocalDateTime date) {
        return storage.addTask(new RepeatableTask(name, desc, creator, repeatCount, date));
    }

    public String[] getAllTaskNames() {
        return storage.getAllTaskNames().toArray(new String[0]);
    }

    public Task getTask(String name) {
        return storage.getTask(name);
    }

    public boolean deleteTask(String name) {
        return storage.removeTask(name);
    }

    public boolean updateTask(String taskName, String newDescription, LocalDateTime newDeadline,
                              Integer newRepeatCount, LocalDateTime newRepeatDate) {
        Task task =  storage.getTask(taskName);
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
        return true;
    }
}
