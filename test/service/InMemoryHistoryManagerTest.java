package service;

import model.Task;
import model.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса InMemoryHistoryManager")
class InMemoryHistoryManagerTest {
    private TaskForTests task;
    private EpicForTests epic;

    InMemoryHistoryManager historyManager;

    @BeforeEach
    void beforeEach(){
        task = new TaskForTests(0, "Name", Status.NEW, "Description");
        epic = new EpicForTests(1, "Name", Status.NEW,"Description");
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    @DisplayName("Тест добавления и возврата истории")
    void shouldAddHistoryAndGetHistory() {
        ArrayList<Task> compareHistory = new ArrayList<>();

        compareHistory.add(task);
        historyManager.addHistory(task);

        assertNotNull(historyManager.getHistory(), "История не содержит элементов");
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    @Test
    @DisplayName("Тест переполнения списка историй")
    void shouldAddHistoryOver10Elements() {
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

    private static class TaskForTests extends Task{
        public TaskForTests(Integer id, String name, Status status, String description) {
            super(id, name, status, description);
        }
    }

    private static class EpicForTests extends Task {
        public EpicForTests(Integer id, String name, Status status, String description) {
            super(id, name, status, description);
        }
    }
}