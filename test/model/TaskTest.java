package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Task")
class TaskTest {
    @Test
    @DisplayName("Тест сравнение оригинала задачи со своей копией")
    public void shouldEqualsWithCopy() {
        Task task = new Task(1, "name", Status.NEW, "description");
        Task copyTask = new Task(task.getId(), "name", Status.NEW, "description");

        assertTaskEquals(task, copyTask);
    }

    private static void assertTaskEquals(Task task, Task copyTask) {
        assertEquals(task.getId(), copyTask.getId(), "id " + "должны совпадать.");
        assertEquals(task.getName(), copyTask.getName(), "name  " + "должны совпадать.");
        assertEquals(task.getStatus(), copyTask.getStatus(), "status " + "должны совпадать.");
        assertEquals(task.getDescription(), copyTask.getDescription(), "description " + "должны совпадать.");
    }
}