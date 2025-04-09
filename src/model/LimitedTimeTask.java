package src.model;

import java.time.LocalDateTime;

public class LimitedTimeTask extends Task {
    private LocalDateTime deadline;

    public LimitedTimeTask(String taskName, String description, String creatorName, LocalDateTime deadline) {
        super(taskName, description, creatorName);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public String getTaskDetails() {
        return "LimitedTime [Name: " + getTaskName() + ", Description: " + getDescription()
                + ", Creator: " + getCreatorName() + ", Deadline: " + getDeadline() +"]";
    }

    @Override
    public String serialize() {
        return "LIMITED|" + getTaskName() + "|" + getDescription() + "|" + getCreatorName() + "|" + getDeadline();
    }
}
