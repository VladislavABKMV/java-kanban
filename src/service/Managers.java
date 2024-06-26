package service;

import service.history.HistoryManager;
import service.history.InMemoryHistoryManager;
import service.task.InMemoryTaskManager;
import service.task.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
