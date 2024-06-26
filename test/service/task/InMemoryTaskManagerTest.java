package service.task;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.history.InMemoryHistoryManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

@DisplayName("Тестирование класса InMemoryTaskManager")
class InMemoryTaskManagerTest {
    @Test
    @DisplayName("Тест метода getAllTask")
    void getAllTask_equalsWithCompareList() {
        //given
        TaskManager manager = getTaskManager();
        Task task = getTask();
        Task compareTask = manager.createTask(task);

        //when
        ArrayList<Task> compareTasks = new ArrayList<>();
        compareTasks.add(compareTask);

        //then
        assertArrayEquals(manager.getAllTask().toArray(), compareTasks.toArray(), "Список всех задач " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода getAllEpic")
    void getAllEpic_equalsWithCompareList() {
        //given
        TaskManager manager = getTaskManager();
        Epic epic = getEpic();
        Epic comapreEpic = manager.createEpic(epic);

        //when
        ArrayList<Epic> compareEpics = new ArrayList<>();
        compareEpics.add(comapreEpic);

        //then
        assertArrayEquals(manager.getAllEpic().toArray(), compareEpics.toArray(), "Список всех эпиков " +
                "отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода getAllSubTask")
    void getAllSubTask_equalsWithCompareList() {
        //given
        TaskManager manager = getTaskManager();
        Epic epic = getEpic();
        Epic cmpareEpic =  manager.createEpic(epic);
        SubTask compareSubtask =  manager.createSubTask(getSubTask(cmpareEpic.getId()));

        //when
        ArrayList<SubTask> compareSubTasks = new ArrayList<>();
        compareSubTasks.add(compareSubtask);

        //then
        assertArrayEquals(manager.getAllSubTask().toArray(), compareSubTasks.toArray(), "Список всех " +
                "подзадач отличается от ожидаемого");
    }

    @Test
    @DisplayName("Тест метода deleteAllTask")
    void deleteAllTask_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Task task = getTask();

        //when
        manager.createTask(task);
        manager.deleteAllTask();

        //then
        assertTrue(manager.getAllTask().isEmpty(), "НЕ все задачи удалены");
    }

    @Test
    @DisplayName("Тест метода deleteAllEpic")
    void deleteAllEpic_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Epic epic = getEpic();

        //when
        manager.createEpic(epic);
        manager.deleteAllEpic();

        //then
        assertTrue(manager.getAllEpic().isEmpty(), "НЕ все эпики удалены");
    }

    @Test
    @DisplayName("Тест метода deleteAllSubTask")
    void deleteAllSubTask_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Epic epic = getEpic();
        Epic testEpic = manager.createEpic(epic);

        //when
        manager.createSubTask(getSubTask(testEpic.getId()));
        manager.deleteAllSubTask();

        //then
        assertTrue(manager.getAllSubTask().isEmpty(), "НЕ все подзадачи удалены");
    }

    @Test
    @DisplayName("Тест метода getTask")
    void getTask_equalsWithFullCopy() {
        //given
        TaskManager manager = getTaskManager();

        //when
        Task comareTask = manager.createTask(getTask());

        //then
        assertEquals(manager.getTask(comareTask.getId()), comareTask, "Задачи не совпадают");
    }

    @Test
    @DisplayName("Тест метода getEpic")
    void getEpic_equalsWithFullCopy() {
        //given
        TaskManager manager = getTaskManager();

        //when
        Epic compareEpic = manager.createEpic(getEpic());

        //then
        assertEquals(manager.getEpic(compareEpic.getId()), compareEpic, "Эпики не совпадают");
    }

    @Test
    @DisplayName("Тест метода getSubTask")
    void getSubTask_equalsWithFullCopy() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());

        //when
        SubTask comareSubTask = manager.createSubTask(getSubTask(testEpic.getId()));

        //then
        assertEquals(manager.getSubTask(comareSubTask.getId()), comareSubTask, "Подзадачи не совпадают");
    }

    @Test
    @DisplayName("Тест метода createTask")
    void createTask_shouldMatchItsCopy() {
        //given
        TaskManager manager = getTaskManager();

        //when
        Task compareTask = manager.createTask(getTask());

        //then
        assertEquals(manager.getTask(compareTask.getId()), compareTask, "Задачи различаются");
    }

    @Test
    @DisplayName("Тест метода createEpic")
    void createEpic_shouldMatchItsCopy() {
        //given
        TaskManager manager = getTaskManager();

        //when
        Epic compareEpic = manager.createEpic(getEpic());

        //then
        assertEquals(manager.getEpic(compareEpic.getId()), compareEpic, "Эпики различаются");
    }

    @Test
    @DisplayName("Тест метода createSubTask")
    void createSubTask_shouldMatchItsCopy() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());

        //when
        SubTask compareSubTask = manager.createSubTask(getSubTask(testEpic.getId()));

        //then
        assertEquals(manager.getSubTask(compareSubTask.getId()), compareSubTask, "Подзадачи различаются");
    }

    @Test
    @DisplayName("Тест метода updateTask")
    void updateTask_shouldTheOriginalChange() {
        //given
        TaskManager manager = getTaskManager();
        Task oldTask = manager.createTask(getTask());
        Task compareTask = new Task(oldTask.getId(),"new task name", Status.DONE, "new desc task");

        //when
        manager.updateTask(compareTask);

        //then
        assertEquals(manager.getTask(oldTask.getId()), compareTask, "Задача не была обновлена");
    }

    @Test
    @DisplayName("Тест метода updateEpic")
    void updateEpic_shouldTheOriginalChange() {
        //given
        TaskManager manager = getTaskManager();
        Epic oldEpic = manager.createEpic(getEpic());
        Epic compareEpic = new Epic(oldEpic.getId(),"new epic name", Status.DONE, "new desc epic");

        //when
        manager.updateEpic(compareEpic);

        //then
        assertEquals(manager.getEpic(oldEpic.getId()), compareEpic, "Эпик не был обновлен");
    }

    @Test
    @DisplayName("Тест метода updateSubTask")
    void updateSubTask_shouldTheOriginalChange() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());
        SubTask oldSubTask = manager.createSubTask(getSubTask(testEpic.getId()));
        SubTask compareSubTask = new SubTask(oldSubTask.getId(),"new SubTask name", Status.DONE,
                "new desc subTask", testEpic.getId());

        //when
        manager.updateSubTask(compareSubTask);

        //then
        assertEquals(manager.getSubTask(oldSubTask.getId()), compareSubTask, "Подзадача не была обновлена");
    }

    @Test
    @DisplayName("Тест метода deleteTask")
    void deleteTask_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Task testTask = manager.createTask(getTask());

        //when
        manager.deleteTask(testTask.getId());

        //then
        assertTrue(manager.getAllTask().isEmpty(), "Задача не была удалена");
    }

    @Test
    @DisplayName("Тест метода deleteEpic")
    void deleteEpic_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());

        //when
        manager.deleteEpic(testEpic.getId());

        //then
        assertTrue(manager.getAllEpic().isEmpty(), "Эпик не был удален");
    }

    @Test
    @DisplayName("Тест метода deleteSubTask")
    void deleteSubTask_shouldReturnTrue() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());
        SubTask testSubTask = manager.createSubTask(getSubTask(testEpic.getId()));

        //when
        manager.deleteSubTask(testSubTask.getId());

        //then
        assertTrue(manager.getAllSubTask().isEmpty(), "Подзадача не была удалена");
    }

    @Test
    @DisplayName("Тест метода getSubTasksForEpic")
    void getSubTasksForEpic_shouldMatchWithCompareList() {
        //given
        TaskManager manager = getTaskManager();
        Epic testEpic = manager.createEpic(getEpic());
        SubTask testSubTask = manager.createSubTask(getSubTask(testEpic.getId()));
        ArrayList<SubTask> compareSubTaskForEpic = new ArrayList<>();

        //when
        compareSubTaskForEpic.add(testSubTask);

        //then
        assertArrayEquals(manager.getSubTasksForEpic(testEpic).toArray(), compareSubTaskForEpic.toArray(),
                "Подзадачи принадлежащие эпику не совпадают с ожидаемым значением");
    }

    @Test
    @DisplayName("Тест метода getHistory")
    void getHistory_shouldReturnHistoryList() {
        //given
        TaskManager manager = getTaskManager();
        Task testTask = manager.createTask(getTask());
        Epic testEpic = manager.createEpic(getEpic());
        SubTask testSubTask = manager.createSubTask(getSubTask(testEpic.getId()));
        ArrayList<Task> compareHistory = new ArrayList<>();

        //when
        compareHistory.add(testTask);
        compareHistory.add(testEpic);
        compareHistory.add(testSubTask);
        manager.getTask(testTask.getId());
        manager.getEpic(testEpic.getId());
        manager.getSubTask(testSubTask.getId());

        //then
        assertArrayEquals(manager.getHistory().toArray(), compareHistory.toArray(), "История не совпадает с " +
                "ожидаемым значением");
    }

    private static Task getTask() {
        return new Task("name Task", Status.NEW, "desc task");
    }

    private static Epic getEpic() {
        return new Epic("name Epic", Status.NEW, "desc epic");
    }

    private static SubTask getSubTask(int epicId) {
        return new SubTask("name SubTask", Status.NEW, "desc subTask", epicId);
    }

    private static TaskManager getTaskManager() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }
}