package service.task;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.history.HistoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private final HistoryManager historyManager;

    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;

    private int idCount = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllTask() {
        for (Task task : tasks.values()) {
            historyManager.removeHistory(task.getId());
        }

        tasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        for (Epic epic : epics.values()) {
            historyManager.removeHistory(epic.getId());
        }
        epics.clear();

        deleteAllSubTask();
    }

    @Override
    public void deleteAllSubTask() {
        if (!epics.isEmpty()) {
            for (Epic epic: epics.values()) {
                epic.setSubTaskIds(new ArrayList<>());
                updateStatusEpic(epic);
            }
        }

        for (SubTask subTask : subTasks.values()) {
            historyManager.removeHistory(subTask.getId());
        }

        subTasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.addHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.addHistory(subTasks.get(id));
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
        epic.addSubTask(subTask.getId());
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
        historyManager.removeHistory(id);
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
       Epic epic = epics.remove(id);
       historyManager.removeHistory(epic.getId());

       List<SubTask> targetEpicSubTasks = getSubTasksForEpic(epic);

        for (SubTask subTask: targetEpicSubTasks) {
            subTasks.remove(subTask.getId());
            historyManager.removeHistory(subTask.getId());
        }
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTask =  subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());

        historyManager.removeHistory(subTask.getId());
        epic.removeSubTask(subTask.getId());
        updateStatusEpic(epic);
    }

    @Override
    public List<SubTask> getSubTasksForEpic(Epic epic) {
        ArrayList<SubTask> subTaskForTargetEpic = new ArrayList<>();
        for (int subTaskId: epic.getSubTaskIds()) {
          subTaskForTargetEpic.add(subTasks.get(subTaskId));
        }
        return subTaskForTargetEpic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateStatusEpic(Epic epic) {
        int newSubTaskCount = 0;
        int doneSubTaskCount = 0;

        for (int subTaskId: epic.getSubTaskIds()) {
            if (subTasks.get(subTaskId).getStatus() == Status.NEW) {
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
