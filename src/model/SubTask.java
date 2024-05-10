package model;

public class SubTask extends Task{
    private Epic epic;

    public SubTask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;
    }

    public Epic getTargetEpicId() {
        return epic;
    }

    public void setTargetEpicId(Epic epic) {
        this.epic = epic;
    }

    public void updateStatus() {
        status = Status.NEW;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                "epic=" + epic +
                '}';
    }
}
