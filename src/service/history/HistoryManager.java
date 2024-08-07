package service.history;

import model.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();

    void addHistory(Task task);

    void removeHistory(int id);
}
