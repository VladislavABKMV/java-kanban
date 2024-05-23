package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, SubTask> subTasks;

    public Epic(String name, Status status, String description) {
        super(name, status, description);
        subTasks = new HashMap<>() {};
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
        this.subTasks = new HashMap<>();
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(SubTask subTask){
        if (this.equals(subTask.getEpic())){
            subTasks.put(subTask.getId(), subTask);
            updateStatus();
        }
    }

    public void removeSubTask(SubTask task){
        subTasks.remove(task.getId());
        updateStatus();
    }

    @Override
    public void updateStatus() {
        int subTasksDoneCount = 0;

        for (SubTask subTask : subTasks.values()) {
            if(subTask.getStatus() != Status.NEW) {
                subTasksDoneCount++;
            }
        }

        if ( subTasks.size() == subTasksDoneCount  && subTasksDoneCount != 0) {
            status = Status.DONE;
        } else if (0 < subTasksDoneCount && subTasksDoneCount < subTasks.size()) {
            status = Status.IN_PROGRESS;
        } else {
            status = Status.NEW;
        }
    }

    @Override
    public String toString() {
        ArrayList<Integer> subTaskIds = new ArrayList<>();

        for (SubTask subTask: subTasks.values()) {
            subTaskIds.add(subTask.getId());

        }

        return "Epic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subTaskIds='" + subTaskIds + '\'' +
                '}';
    }
}
