package src.model;

import java.time.LocalDateTime;

public class RepeatableTask extends Task {
    private int repeatCount;
    private LocalDateTime repeatDate;

    public RepeatableTask(String taskName, String description, String creatorName, int repeatCount, LocalDateTime repeatDate) {
        super(taskName, description, creatorName);
        this.repeatCount = repeatCount;
        this.repeatDate = repeatDate;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public LocalDateTime getRepeatDate() {
        return repeatDate;
    }

    public void setRepeatDate(LocalDateTime repeatDate) {
        this.repeatDate = repeatDate;
    }

    @Override
    public String getTaskDetails() {
        return "Repeatable [Name: " + getTaskName() + ", Description: " + getDescription()
                + ", Creator: " + getCreatorName() + ", Repeat Count: " + getRepeatCount()
                + ", Repeat Date: " + getRepeatDate() + "]";
    }

    @Override
    public String serialize() {
        return "REPEATABLE|" + getTaskName() + "|" + getDescription() + "|" + getCreatorName() + "|" + getRepeatCount()
                + "|" + getRepeatDate();
    }
}
