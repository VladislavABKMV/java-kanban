package service;

import model.Task;

import java.util.List;

public interface HistoryManager {
    public List<Task> getHistory();

    public void addHistory(Task task);
}
