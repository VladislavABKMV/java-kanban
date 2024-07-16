package service.history;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса InMemoryHistoryManager")
class InMemoryHistoryManagerTest {

    private static HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = getHistory();
    }

    @Test
    @DisplayName("Тест добавления и возврата истории")
    void addHistory_shouldReturnNotNull_andGetHistoryEquals_returnTrue_withFullCopy() {
        //given
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();

        //when
        compareHistory.add(task);
        historyManager.addHistory(task);

        //then
        assertNotNull(historyManager.getHistory(), "История не содержит элементов");
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    @Test
    @DisplayName("Тест на отсутствие повторов в истории")
    void addHistory_shouldNotRepeatElements() {
        //given
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();

        //when
        compareHistory.add(task);
        historyManager.addHistory(task);
        historyManager.addHistory(task);

        //then
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    @Test
    @DisplayName("Тест удаления истории из начала")
    void removeHistory_firstElementShouldRemove() {
        //given
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();
        Epic epic = getEpic();
        SubTask subTask = getSubTask();

        //when
        compareHistory.add(epic);
        compareHistory.add(subTask);
        historyManager.addHistory(task);
        historyManager.addHistory(epic);
        historyManager.addHistory(subTask);
        historyManager.removeHistory(task.getId());

        //then
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории.");
    }

    @Test
    @DisplayName("Тест удаления истории из середины")
    void removeHistory_middleElementShouldRemove() {
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();
        Epic epic = getEpic();
        SubTask subTask = getSubTask();

        //when
        compareHistory.add(task);
        compareHistory.add(subTask);
        historyManager.addHistory(task);
        historyManager.addHistory(epic);
        historyManager.addHistory(subTask);
        historyManager.removeHistory(epic.getId());

        //then
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    @Test
    @DisplayName("Тест удаления истории с конца")
    void removeHistory_lastElementShouldRemove() {
        ArrayList<Task> compareHistory = new ArrayList<>();
        Task task = getTask();
        Epic epic = getEpic();
        SubTask subTask = getSubTask();

        //when
        compareHistory.add(task);
        compareHistory.add(epic);
        historyManager.addHistory(task);
        historyManager.addHistory(epic);
        historyManager.addHistory(subTask);
        historyManager.removeHistory(subTask.getId());

        //then
        assertArrayEquals(historyManager.getHistory().toArray(), compareHistory.toArray(),
                "Ожидалось другое наполнение списка истории");
    }

    private static Task getTask() {
        return new Task(0, "Name", Status.NEW, "Description");
    }

    private static Epic getEpic() {
        return new Epic(1, "Name", Status.NEW,"Description");
    }

    private static SubTask getSubTask() {
        return new SubTask(2,"name", Status.NEW,"description", 1);
    }

    private static HistoryManager getHistory() {
        return new InMemoryHistoryManager();
    }
}