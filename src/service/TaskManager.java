package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;

    private int idCount = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    public ArrayList<Task> getAll() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAll() {
        tasks.clear();
    }

    public void deleteAllEpic() {
        epics.clear();
    }

    public void deleteAllSubTask() {
        subTasks.clear();
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }

    public Task create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateId());
        subTask.getEpic().addSubTask(subTask);
        subTasks.put(subTask.getId(), subTask);

        return subTask;
    }

    public void update(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic updateEpic) {
        Epic epic = epics.get(updateEpic.getId());
        if (!epic.equals(updateEpic)) {
            return;
        }

        epic.setName(updateEpic.getName());
        epic.setDescription(updateEpic.getDescription());
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        subTask.getEpic().addSubTask(subTask);
    }

    public void delete(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
       Epic epic = epics.remove(id);
       ArrayList<SubTask> targetEpicSubTasks = getSubTasksForEpic(epic);

        for (SubTask subTask: targetEpicSubTasks) {
            subTasks.remove(subTask.getId());
        }
    }

    public void deleteSubTask(int id) {
        SubTask subTask =  subTasks.remove(id);

        subTask.getEpic().removeSubTask(subTask);
    }

    public ArrayList<SubTask> getSubTasksForEpic(Epic epic) {
        return new ArrayList<>(epic.getSubTasks().values());
    }

    private int generateId() {
        return idCount++;
    }
}
