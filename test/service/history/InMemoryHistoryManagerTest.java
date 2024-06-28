package service.history;

import model.Epic;
import model.Task;
import model.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса InMemoryHistoryManager")
class InMemoryHistoryManagerTest {
    @Test
    @DisplayName("Тест добавления и возврата истории")
    void addHistory_shouldReturnNotNull_andGetHistoryEquals_returnTrue_withFullCopy() {
        HistoryManager historyManager = getHistory();
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();

        compareHistory.add(task);
        historyManager.addHistory(task);

        assertNotNull(historyManager.getHistory(), "История не содержит элементов");
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    @Test
    @DisplayName("Тест переполнения списка историй")
    void addHistory_ListHasASizeOf10Elements_withOverFlow() {
        HistoryManager historyManager = getHistory();
        Task task = getTask();
        Epic epic = getEpic();
        int maxSizeHistory = 10;

        historyManager.addHistory(task);
        historyManager.addHistory(task);
        for (int i = 0 ; i < 10; i++) {
            historyManager.addHistory(epic);
        }

        assertEquals(historyManager.getHistory().size(), maxSizeHistory, "Размер списка отличается от " +
                "ожидаемого");
        assertEquals(historyManager.getHistory().getFirst(), epic, "Первый элемент списка отличается от " +
                "ожидаемого");
    }

    private static Task getTask() {
        return new Task(0, "Name", Status.NEW, "Description");
    }

    private static Epic getEpic() {
        return new Epic(1, "Name", Status.NEW,"Description");
    }

    private static HistoryManager getHistory() {
        return new InMemoryHistoryManager();
    }
}