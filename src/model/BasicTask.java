package src.model;

public class BasicTask extends Task {
    public BasicTask(String taskName, String description, String creatorName) {
        super(taskName, description, creatorName);
    }

    @Override
    public String getTaskDetails() {
        return "BasicTask [Name: " + getTaskName() + ", Description: " + getDescription()
                + ", Creator: " + getCreatorName() + "]";
    }

    @Override
    public String serialize() {
        return "BASIC|" + getTaskName() + "|" + getDescription() + "|" + getCreatorName();
    }
}
