package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {

    public List<Task> getAllTask();

    public List<Epic> getAllEpic();

    public List<SubTask> getAllSubTask();

    public void deleteAllTask();

    public void deleteAllEpic();

    public void deleteAllSubTask();

    public Task getTask(int id);

    public Epic getEpic(int id);

    public SubTask getSubTask(int id);

    public Task createTask(Task task);

    public Epic createEpic(Epic epic);

    public SubTask createSubTask(SubTask subTask);

    public void updateTask(Task task);

    public void updateEpic(Epic updateEpic);

    public void updateSubTask(SubTask subTask);

    public void deleteTask(int id);

    public void deleteEpic(int id);

    public void deleteSubTask(int id);

    public List<SubTask> getSubTasksForEpic(Epic epic);
    public List<Task> getHistory();
}
