package model;

public class SubTask extends Task{
    private final Epic epic;

    public SubTask(String name, Status status, String description, Epic epic) {
        super(name, status, description);
        this.epic = epic;
    }

    public SubTask(int id, String name, Status status, String description, Epic epic) {
        super(id, name, status, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public void updateStatus() {
        switch (status) {
            case NEW:
                status = Status.IN_PROGRESS;
                break;
            case IN_PROGRESS:
                status = Status.DONE;
                break;
        }
        epic.updateStatus();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", epicId='" + epic.getId() + '\'' +
                '}';
    }
}
