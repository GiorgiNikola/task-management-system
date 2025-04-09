package src.io;

import src.model.LimitedTimeTask;
import src.model.RepeatableTask;
import src.model.Task;
import src.service.TaskService;
import src.utils.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskService taskService = new TaskService();
    private String username;

    public void start() {
        System.out.println("Welcome to task management system!");
        username = promptUsername();

        while (true) {
            System.out.println("\nCommands: CREATE, LIST, UPDATE, DELETE, GET, EXIT");
            System.out.println("CREATE: Creates a new task");
            System.out.println("LIST: Shows the list of tasks");
            System.out.println("UPDATE: Updates the existing task");
            System.out.println("DELETE: Deletes the task");
            System.out.println("GET: Get the task");
            System.out.println("EXIT: Exit from console");
            System.out.println("Enter command: ");
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {
                case "CREATE": createTask(); break;
                case "LIST": printTaskList(); break;
                case "UPDATE": updateTask(); break;
                case "DELETE": deleteTask(); break;
                case "GET": getTaskInfo(); break;
                case "EXIT": System.exit(0);
                default: System.out.println("Invalid command!");;
            }
        }
    }

    private void createTask() {
        System.out.println("Task types: Basic, LimitedTime, Repeatable");
        System.out.println("Enter task type: ");
        String taskType = scanner.nextLine().trim().toLowerCase();
        if (!isValidTaskType(taskType)) {
            System.out.println("Invalid task type!");
            return;
        }
        System.out.println("Enter task name: ");
        String taskName = scanner.nextLine();
        if (taskService.getTask(taskName) != null) {
            System.out.println("Task with this name already exists!");
            return;
        }
        System.out.println("Enter task description: ");
        String taskDescription = scanner.nextLine();
        if (taskType.equals("basic")) {
            if (taskService.createBasicTask(taskName, taskDescription, username)) {
                System.out.println("Task created successfully!");
            }
        } else if (taskType.equals("limitedtime")) {
            System.out.println("Enter the deadline");
            LocalDateTime deadline = Utils.getDateFromConsole(scanner);
            if (taskService.createLimitedTask(taskName, taskDescription, username, deadline)) {
                System.out.println("Task added successfully!");
            }
        } else {
            int repeatCount = promptRepeatCount();
            System.out.println("Enter the repeat date");
            LocalDateTime date = Utils.getDateFromConsole(scanner);
            if (taskService.createRepeatableTask(taskName, taskDescription, username, repeatCount, date)) {
                System.out.println("Task added successfully!");
            }
        }

    }

    private void deleteTask() {
        System.out.println("Enter task name to delete: ");
        String taskName = scanner.nextLine();
        if (taskService.deleteTask(taskName)) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task does not exist!");
        }
    }

    private void updateTask() {
        System.out.println("Enter task name to edit: ");
        String taskName = scanner.nextLine();
        Task task = taskService.getTask(taskName);
        if (task == null) {
            System.out.println("Task does not exist!");
            return;
        }

        System.out.println("Enter task description: ");
        String taskDescription = scanner.nextLine();

        LocalDateTime deadline = null;
        Integer repeatCount = null;
        LocalDateTime repeatDate = null;

        if (task instanceof LimitedTimeTask) {
            System.out.println("Enter the deadline");
            deadline = Utils.getDateFromConsole(scanner);
        }

        if (task instanceof RepeatableTask) {
            repeatCount = promptRepeatCount();
            System.out.println("Enter the repeat date");
            repeatDate = Utils.getDateFromConsole(scanner);
        }

        if (taskService.updateTask(taskName, taskDescription, deadline, repeatCount, repeatDate)) {
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Could not update task!");
        }
    }

    private void printTaskList() {
        List<String> names = taskService.getAllTaskNames();
        if (names.isEmpty()) {
            System.out.println("No tasks found.");
        }
        else {
            System.out.println("Tasks: ");
            for (String name : names) {
                System.out.println("- " + name);
            }
        }
    }

    private void getTaskInfo() {
        System.out.println("Enter task name: ");
        String taskName = scanner.nextLine();
        if (taskName.isEmpty()) {
            System.out.println("Task name cannot be empty.");
            return;
        }

        Task task = taskService.getTask(taskName);
        if (task != null) {
            System.out.println(task.getTaskDetails());
        }else {
            System.out.println("Task not found!");
        }
    }

    private String promptUsername() {
        while (true) {
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            if (username.matches("^[a-zA-Z0-9_]{3,15}$")) {
                return username;
            }else {
                System.out.println("Invalid username!");
                System.out.println("Username length must be between 3 and 15 characters!");
                System.out.println("Allowed characters: letters, digits, underscore");
            }
        }
    }

    private int promptRepeatCount() {
        while (true) {
            System.out.print("Enter the number of repeats: ");
            String input = scanner.nextLine();
            if (input.chars().allMatch(Character::isDigit)) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid number. Please enter a valid integer.");
        }
    }

    private boolean isValidTaskType(String type) {
        return type.equals("basic") || type.equals("limitedtime") || type.equals("repeatable");
    }
}
