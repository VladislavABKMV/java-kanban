package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Epic")
class EpicTest {
    private Epic epic;

    @BeforeEach
    void beforeEach() {
        epic = new Epic(0, "name", Status.NEW, "description");
    }

    @Test
    @DisplayName("Тест на совпадение двух эпиков")
    public void shouldEqualsWithCopy() {
        Epic compareEpic = new Epic(epic.getId(), "name", epic.getStatus(), "description");
        
        assertEpicEquals(epic, compareEpic);
    }

    @Test
    @DisplayName("Тест добавления подзадач в эпик, с проверкой на дубликаты")
    public void shouldAddSubTask() {
        SubTaskForTest subTask = new SubTaskForTest(1, "name", Status.NEW, "description", epic.getId());
        int expectedSizeList = 1;

        epic.addSubTask(subTask);
        epic.addSubTask(subTask);

        assertEquals(epic.getSubTaskIds().size(), expectedSizeList, "Количество подзадач в Эпике отличается от " +
                "ожидаемого");
    }

    @Test
    @DisplayName("Тест удаления подзадач из эпика")
    public void shouldRemoveSubtask() {
        SubTaskForTest subTask = new SubTaskForTest(1, "name", Status.NEW, "description", epic.getId());

        epic.addSubTask(subTask);
        epic.addSubTask(subTask);
        epic.removeSubTask(subTask);

        assertTrue(epic.getSubTaskIds().isEmpty(), "Ожидается отсутствие подзадач");
    }

    private static void assertEpicEquals(Epic epic, Epic copyEpic) {
        assertEquals(epic.getId(), copyEpic.getId(), "id " + "должны совпадать.");
        assertEquals(epic.getName(), copyEpic.getName(), "name  " + "должны совпадать.");
        assertEquals(epic.getStatus(), copyEpic.getStatus(), "status " + "должны совпадать.");
        assertEquals(epic.getDescription(), copyEpic.getDescription(), "description " + "должны совпадать.");
    }

    private static class SubTaskForTest extends SubTask {
        public SubTaskForTest(int id, String name, Status status, String description, Integer epicId) {
            super(id, name, status, description, epicId);
        }
    }
}