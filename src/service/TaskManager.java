package service;

import model.Epic;
import model.Status;
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

    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllEpic() {
        epics.clear();
        subTasks.clear();
    }

    public void deleteAllSubTask() {
        subTasks.clear();

        for (Epic epic: epics.values()) {
            epic.setSubTaskIds(new ArrayList<>());
        }
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }

    public Task createTask(Task task) {
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
        Epic epic = epics.get(subTask.getEpicId());

        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);
        epic.addSubTask(subTask);
        updateStatusEpic(epic);

        return subTask;
    }

    public void updateTask(Task task) {
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
        Epic epic = epics.get(subTask.getEpicId());

        subTasks.put(subTask.getId(), subTask);
        updateStatusEpic(epic);
    }

    public void deleteTask(int id) {
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
        Epic epic = epics.get(subTask.getEpicId());

        epic.removeSubTask(subTask);
        updateStatusEpic(epic);
    }

    public ArrayList<SubTask> getSubTasksForEpic(Epic epic) {
        ArrayList<SubTask> subTaskForTargetEpic = new ArrayList<>();
        for (int subTaskId: epic.getSubTaskIds()) {
          subTaskForTargetEpic.add(subTasks.get(subTaskId));
        }
        return subTaskForTargetEpic;
    }

    private void updateStatusEpic(Epic epic) {
        int newSubTaskCount = 0;
        int doneSubTaskCount = 0;

        for (int subTaskId: epic.getSubTaskIds()) {
            if(subTasks.get(subTaskId).getStatus() == Status.NEW) {
                newSubTaskCount++;
            } else if (subTasks.get(subTaskId).getStatus() == Status.DONE) {
                doneSubTaskCount++;
            }
        }

        if (epic.getSubTaskIds().isEmpty() || newSubTaskCount == epic.getSubTaskIds().size()) {
            epic.setStatus(Status.NEW);
        } else if (doneSubTaskCount == epic.getSubTaskIds().size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private int generateId() {
        return idCount++;
    }
}
