package model;

public class SubTask extends Task{
    private final Integer epicId;

    public SubTask(String name, Status status, String description, Integer epicId) {
        super(name, status, description);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, Status status, String description, Integer epicId) {
        super(id, name, status, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", epicId='" + epicId + '\'' +
                '}';
    }
}
