package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIds;

    public Epic(String name, Status status, String description) {
        super(name, status, description);
        subTaskIds = new ArrayList<>();
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
        this.subTaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }


    public void addSubTask(SubTask subTask){
        if (!subTaskIds.contains(subTask.getId())) {
            subTaskIds.add(subTask.getId());
        }
    }

    public void removeSubTask(SubTask subTask){
        subTaskIds.remove((Integer) subTask.getId());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subTaskIds='" + subTaskIds + '\'' +
                '}';
    }
}
