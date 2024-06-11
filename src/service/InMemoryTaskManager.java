package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;
    private final ArrayList<Task> history;

    private int idCount = 0;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.history = new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubTask() {
        subTasks.clear();

        for (Epic epic: epics.values()) {
            epic.setSubTaskIds(new ArrayList<>());
            updateStatusEpic(epic);
        }
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());

        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);
        epic.addSubTask(subTask);
        updateStatusEpic(epic);

        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        Epic epic = epics.get(updateEpic.getId());
        if (!epic.equals(updateEpic)) {
            return;
        }

        epic.setName(updateEpic.getName());
        epic.setDescription(updateEpic.getDescription());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());

        subTasks.put(subTask.getId(), subTask);
        updateStatusEpic(epic);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
       Epic epic = epics.remove(id);
       ArrayList<SubTask> targetEpicSubTasks = getSubTasksForEpic(epic);

        for (SubTask subTask: targetEpicSubTasks) {
            subTasks.remove(subTask.getId());
        }
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTask =  subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());

        epic.removeSubTask(subTask);
        updateStatusEpic(epic);
    }

    @Override
    public ArrayList<SubTask> getSubTasksForEpic(Epic epic) {
        ArrayList<SubTask> subTaskForTargetEpic = new ArrayList<>();
        for (int subTaskId: epic.getSubTaskIds()) {
          subTaskForTargetEpic.add(subTasks.get(subTaskId));
        }
        return subTaskForTargetEpic;
    }

    // доделать
    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(tasks.values());
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
    // доделать
    private void addHistory(Task task) {
        history.add(task);
    }

    private int generateId() {
        return idCount++;
    }
}
