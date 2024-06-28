package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса SubTask")
class SubTaskTest {
    @Test
    @DisplayName("Тест сравнение подзадачи со своей копией")
    public void equals_returnTrue_withFullCopy() {
        //given
        SubTask subTask = new SubTask(1, "name", Status.NEW, "description", 0);
        SubTask copySubTask = new SubTask(subTask.getId(), "name", Status.NEW,
                "description", 0);

        //then
        assertSubTaskEquals(subTask, copySubTask);
    }

    private static void assertSubTaskEquals(SubTask subTask, SubTask copySubTask) {
        assertEquals(subTask.getId(), copySubTask.getId(), "id " + "должны совпадать.");
        assertEquals(subTask.getName(), copySubTask.getName(), "name  " + "должны совпадать.");
        assertEquals(subTask.getStatus(), copySubTask.getStatus(), "status " + "должны совпадать.");
        assertEquals(subTask.getDescription(), copySubTask.getDescription(),
                "description " + "должны совпадать.");
        assertEquals(subTask.getEpicId(), copySubTask.getEpicId(), "epicId " + "должны совпадать.");
    }
}