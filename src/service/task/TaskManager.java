package service.task;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<SubTask> getAllSubTask();

    void deleteAllTask();

    void deleteAllEpic();

    void deleteAllSubTask();

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);

    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubTask(SubTask subTask);

    void updateTask(Task task);

    void updateEpic(Epic updateEpic);

    void updateSubTask(SubTask subTask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubTask(int id);

    List<SubTask> getSubTasksForEpic(Epic epic);

    List<Task> getHistory();
}
