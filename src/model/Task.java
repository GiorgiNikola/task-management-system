package src.model;

public abstract class Task {
    private String taskName;
    private String description;
    private String creatorName;

    public Task(String taskName, String description, String creatorName) {
        this.taskName = taskName;
        this.description = description;
        this.creatorName = creatorName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public abstract String getTaskDetails();
}
