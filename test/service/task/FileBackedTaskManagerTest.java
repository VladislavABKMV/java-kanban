package service.task;


import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.history.InMemoryHistoryManager;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса FileBackedTaskManager")
class FileBackedTaskManagerTest {
    @Test
    @DisplayName("Тест сохранения и загрузки состояния менеджера задач")
    void loadFromFile_loadState() throws IOException {
        //  given
        File file = getFile();
        TaskManager manager = getTaskManager(file);
        Task task = getTask();
        Epic epic = getEpic();
        SubTask subTask = getSubTask(epic.getId());
        manager.createTask(task);
        manager.createEpic(epic);
        manager.createSubTask(subTask);

        //  when
        TaskManager compareManager = FileBackedTaskManager.loadFromFile(new InMemoryHistoryManager() ,file);

        //  then
        assertTaskManagerEquals(manager, compareManager);
    }

    private static void assertTaskManagerEquals(TaskManager manager, TaskManager copyManager) {
        assertArrayEquals(manager.getAllTask().toArray(), copyManager.getAllTask().toArray(),
                "В менеджерах различаются списки Задач.");
        assertArrayEquals(manager.getAllEpic().toArray(), copyManager.getAllEpic().toArray(),
                "В менеджерах различаются списки Эпиков.");
        assertArrayEquals(manager.getAllSubTask().toArray(), copyManager.getAllSubTask().toArray(),
                "В менеджерах различаются списки Подзадач.");
    }

    private static Task getTask() {
        return new Task("name Task", Status.NEW, "desc task");
    }

    private static Epic getEpic() {
        return new Epic(1,"name Epic", Status.NEW, "desc epic");
    }

    private static SubTask getSubTask(int epicId) {
        return new SubTask("name SubTask", Status.NEW, "desc subTask", epicId);
    }

    private static TaskManager getTaskManager(File file) {
        return FileBackedTaskManager.loadFromFile(new InMemoryHistoryManager(), file);
    }

    private static File getFile() throws IOException {
        return File.createTempFile("Task", "csv");
    }

}