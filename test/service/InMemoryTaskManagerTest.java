package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Тестирование класса InMemoryTaskManager")
class InMemoryTaskManagerTest {
    private Task task;
    private Epic epic;

    private TaskManager manager;

    @BeforeEach
    public void beforeEach() {
        task = new Task("name Task", Status.NEW, "desc task");
        epic = new Epic("name Epic", Status.NEW, "desc epic");

        manager = new InMemoryTaskManager(new HistoryManagerForTest());
    }

    @Test
    @DisplayName("Тест метода getAllTask")
    void shouldGetAllTask() {
        Task compareTask = manager.createTask(task);

        ArrayList<Task> compareTasks = new ArrayList<>();
        compareTasks.add(compareTask);

        assertArrayEquals(manager.getAllTask().toArray(), compareTasks.toArray(), "Список всех задач " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода getAllEpic")
    void shouldGetAllEpic() {
        Epic comapreEpic = manager.createEpic(epic);

        ArrayList<Epic> compareEpics = new ArrayList<>();
        compareEpics.add(comapreEpic);

        assertArrayEquals(manager.getAllEpic().toArray(), compareEpics.toArray(), "Список всех эпиков " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода getAllSubTask")
    void shouldGetAllSubTask() {
        Epic cmpareEpic =  manager.createEpic(epic);
        SubTask compareSubtask =  manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", cmpareEpic.getId()));

        ArrayList<SubTask> compareSubTasks = new ArrayList<>();
        compareSubTasks.add(compareSubtask);

        assertArrayEquals(manager.getAllSubTask().toArray(), compareSubTasks.toArray(), "Список всех " +
                "подзадач отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода deleteAllTask")
    void shouldDeleteAllTask() {
        manager.createTask(task);

        manager.deleteAllTask();

        assertTrue(manager.getAllTask().isEmpty(), "НЕ все задачи удалены");
    }

    @Test
    @DisplayName("Тест метода deleteAllEpic")
    void shouldDeleteAllEpic() {
        manager.createEpic(epic);

        manager.deleteAllEpic();

        assertTrue(manager.getAllEpic().isEmpty(), "НЕ все эпики удалены");
    }

    @Test
    @DisplayName("Тест метода deleteAllSubTask")
    void shouldDeleteAllSubTask() {
        Epic testEpic = manager.createEpic(epic);
        manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));

        manager.deleteAllSubTask();

        assertTrue(manager.getAllSubTask().isEmpty(), "НЕ все подзадачи удалены");
    }

    @Test
    @DisplayName("Тест метода getTask")
    void shouldGetTask() {
        Task comareTask = manager.createTask(task);

        assertEquals(manager.getTask(comareTask.getId()), comareTask, "Задачи не совпадают");
    }

    @Test
    @DisplayName("Тест метода getEpic")
    void shouldGetEpic() {
        Epic compareEpic = manager.createEpic(epic);

        assertEquals(manager.getEpic(compareEpic.getId()), compareEpic, "Эпики не совпадают");
    }

    @Test
    @DisplayName("Тест метода getSubTask")
    void shouldGetSubTask() {
        Epic testEpic = manager.createEpic(epic);
        SubTask comareSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));

        assertEquals(manager.getSubTask(comareSubTask.getId()), comareSubTask, "Подзадачи не совпадают");
    }

    @Test
    @DisplayName("Тест метода createTask")
    void shouldCreateTask() {
        Task compareTask = manager.createTask(task);

        assertEquals(manager.getTask(compareTask.getId()), compareTask, "Задачи различаются");
    }

    @Test
    @DisplayName("Тест метода createEpic")
    void shouldCreateEpic() {
        Epic compareEpic = manager.createEpic(epic);

        assertEquals(manager.getEpic(compareEpic.getId()), compareEpic, "Эпики различаются");
    }

    @Test
    @DisplayName("Тест метода createSubTask")
    void shouldCreateSubTask() {
        Epic testEpic = manager.createEpic(epic);
        SubTask compareSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));

        assertEquals(manager.getSubTask(compareSubTask.getId()), compareSubTask, "Подзадачи различаются");
    }

    @Test
    @DisplayName("Тест метода updateTask")
    void shouldUpdateTask() {
        Task oldTask = manager.createTask(task);
        Task compareTask = new Task(oldTask.getId(),"new task name", Status.DONE, "new desc task");

        manager.updateTask(compareTask);

        assertEquals(manager.getTask(oldTask.getId()), compareTask, "Задача не была обновлена");
    }

    @Test
    @DisplayName("Тест метода updateEpic")
    void shouldUpdateEpic() {
        Epic oldEpic = manager.createEpic(epic);
        Epic compareEpic = new Epic(oldEpic.getId(),"new epic name", Status.DONE, "new desc epic");

        manager.updateEpic(compareEpic);
        assertEquals(manager.getEpic(oldEpic.getId()), compareEpic, "Эпик не был обновлен");
    }

    @Test
    @DisplayName("Тест метода updateSubTask")
    void shouldUpdateSubTask() {
        Epic testEpic = manager.createEpic(epic);
        SubTask oldSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));
        SubTask compareSubTask = new SubTask(oldSubTask.getId(),"new SubTask name", Status.DONE,
                "new desc subTask", testEpic.getId());

        manager.updateSubTask(compareSubTask);
        assertEquals(manager.getSubTask(oldSubTask.getId()), compareSubTask, "Подзадача не была обновлена");
    }

    @Test
    @DisplayName("Тест метода deleteTask")
    void shouldDeleteTask() {
        Task testTask = manager.createTask(task);

        manager.deleteTask(testTask.getId());

        assertTrue(manager.getAllTask().isEmpty(), "Задача не была удалена");
    }

    @Test
    @DisplayName("Тест метода deleteEpic")
    void shouldDeleteEpic() {
        Epic testEpic = manager.createEpic(epic);

        manager.deleteEpic(testEpic.getId());

        assertTrue(manager.getAllEpic().isEmpty(), "Эпик не был удален");
    }

    @Test
    @DisplayName("Тест метода deleteSubTask")
    void shouldDeleteSubTask() {
        Epic testEpic = manager.createEpic(epic);
        SubTask testSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));

        manager.deleteSubTask(testSubTask.getId());

        assertTrue(manager.getAllSubTask().isEmpty(), "Подзадача не была удалена");
    }

    @Test
    @DisplayName("Тест метода getSubTasksForEpic")
    void shouldGetSubTasksForEpic() {
        Epic testEpic = manager.createEpic(epic);
        SubTask testSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));

        ArrayList<SubTask> compareSubTaskForEpic = new ArrayList<>();
        compareSubTaskForEpic.add(testSubTask);

        assertArrayEquals(manager.getSubTasksForEpic(testEpic).toArray(), compareSubTaskForEpic.toArray(),
                "Подзадачи принадлежащие эпику не совпадают с ожидаемым значением");
    }

    @Test
    @DisplayName("Тест метода getHistory")
    void shouldGetHistory() {
        Task testTask = manager.createTask(task);
        Epic testEpic = manager.createEpic(epic);
        SubTask testSubTask = manager.createSubTask(new SubTask("name SubTask", Status.NEW,
                "desc subTask", testEpic.getId()));
        ArrayList<Task> compareHistory = new ArrayList<>();

        compareHistory.add(testTask);
        compareHistory.add(testEpic);
        compareHistory.add(testSubTask);

        manager.getTask(testTask.getId());
        manager.getEpic(testEpic.getId());
        manager.getSubTask(testSubTask.getId());

        assertArrayEquals(manager.getHistory().toArray(), compareHistory.toArray(), "История не совпадает с " +
                "ожидаемым значением");
    }

    private static class HistoryManagerForTest implements HistoryManager {
        List<Task> historyForTest = new ArrayList<>();

        @Override
        public void addHistory(Task task) {
            historyForTest.add(task);
        }

        @Override
        public List<Task> getHistory(){
            return historyForTest;
        }
    }
}