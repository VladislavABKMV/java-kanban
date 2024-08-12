package service;

import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;
import service.task.FileBackedTaskManager;
import service.task.TaskManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTaskManager(getDefaultHistory(), new File("resources/Task.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
