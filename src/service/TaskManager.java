package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;

    public TaskManager(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public Task createTask(Task task) {
        task.setId(task.hashCode());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic task) {
        task.setId(task.hashCode());
        tasks.put(task.getId(), task);
        return task;
    }

    public SubTask createSubTask(SubTask task) {
        task.setId(task.hashCode());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public void update() {

    }

    public void updateEpic() {

    }

    public void updateSubTask() {

    }

    public ArrayList<Task> getAll(){
        return new ArrayList<>(tasks.values());
    }

    public void delete(){

    }
}
